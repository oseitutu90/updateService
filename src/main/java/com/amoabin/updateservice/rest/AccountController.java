package com.amoabin.updateservice.rest;

import com.amoabin.updateservice.entity.UserEntity;
import com.amoabin.updateservice.entity.avro.UserUpdate;
import com.amoabin.updateservice.kafka.UserEntityProducer;
import com.amoabin.updateservice.service.UserBOChanges;
import com.amoabin.updateservice.service.UserService;
import com.amoabin.updateservice.service.util.Indicator;
import com.amoabin.updateservice.service.util.IndicatorDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/account")
@ControllerAdvice
public class AccountController {

    // ... other fields and methods ...

    private final UserService userService;
    private final UserEntityProducer userEntityProducer;
    private final Gson gson;


    @Autowired
    public AccountController(UserService userService, UserEntityProducer userEntityProducer, Gson gson) {
        this.userService = userService;
        this.userEntityProducer = userEntityProducer;
        this.gson = gson;
    }

    @PostMapping("users")
    public ResponseEntity<UserEntity> createUser(@RequestBody String request,
                                                 @RequestHeader(value = "Transaction-Id") String transactionId) {
        try {
            log.info("Request body: {}", request);
            UserEntity user = gson.fromJson(request, UserEntity.class);

            if (userService.existsByEmail(user.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
            }

            user.setTransactionId(UUID.fromString(transactionId));
            UserEntity createdUser = userService.createUser(user);

            // Create a GenericRecord based on the schema of the UserUpdate class
            GenericRecord userEntityRecord = new GenericData.Record(UserUpdate.getClassSchema());

            // Populate the GenericRecord with data from the createdUser object
            // Here, you need to make sure that the fields correspond correctly to the UserUpdate schema
            userEntityRecord.put("user_id", createdUser.getUser_id()); // Ensure the field names match your schema
            userEntityRecord.put("name", createdUser.getName());
            userEntityRecord.put("surname", createdUser.getSurname());
            // ... Populate all fields defined in your Avro schema

            // Publish the GenericRecord to Kafka
            userEntityProducer.publishUserEntity(userEntityRecord);
            log.info("UserEntity published to Kafka topic");

            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            log.error("Error creating user: {}", e.getMessage());
            throw e;
            // Re-throwing so that our ControllerAdvice can handle it
        } catch (Exception e) {
            log.error("Unexpected error creating user", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error creating user");
        }
    }


    @GetMapping("users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("users/uniqueId/{uniqueId}")
    public ResponseEntity<UserEntity> getUserByUniqueId(@PathVariable UUID uniqueId) {
        UserEntity user = userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserBOChanges changes,
                                                 @RequestHeader(value = "Transaction-Id") String transactionId,
                                                 @RequestHeader(value = "Unique-Id") UUID uniqueId) {
        UserEntity existingUser = userService.getUserByUniqueId(uniqueId);
        // Create a custom Gson instance with the IndicatorDeserializer
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Indicator.class, new IndicatorDeserializer())
                .create();

        // Deserialize the JSON input with the custom Gson
        UserEntity user = gson.fromJson(changes.toString(), UserEntity.class);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or transaction ID or unique ID does not match");
        }


        if (!changes.getEmail().equals(existingUser.getEmail())) {
            // Email cannot be updated
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be updated");
        }

        UserEntity updatedUser = userService.updateUser(uniqueId, changes)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    // Exception handling
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponseImpl(ex.getStatusCode(), ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("An error occurred", ex);
        ErrorResponse errorResponse = new ErrorResponseImpl(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Method to convert UserEntity to GenericRecord

    private interface ErrorResponse {
        HttpStatusCode getStatus();

        String getMessage();
    }

    private static class ErrorResponseImpl implements ErrorResponse {
        private final HttpStatusCode status;
        private final String message;

        public ErrorResponseImpl(HttpStatusCode status, String message) {
            this.status = status;
            this.message = message;
        }

        @Override
        public HttpStatusCode getStatus() {
            return status;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}

