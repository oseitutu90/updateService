package com.amoabin.updateservice.service;

import com.amoabin.updateservice.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {

    UserEntity createUser(UserEntity user);

    UserEntity getUserById(Long id);

    List<UserEntity> getAllUsers();

    boolean isRequestIdempotent(String transactionId);

    UserEntity getUserByTransactionId(String transactionId);

    boolean existsByEmail(String email);


    void deleteUser(Long id);

    public Optional<UserEntity> updateUser(UUID uniqueId, UserBOChanges changes);

    UserEntity getUserByUniqueId(UUID uniqueId);
}

