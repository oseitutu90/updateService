package com.amoabin.updateservice.service;

import com.amoabin.updateservice.entity.UserEntity;
import com.amoabin.updateservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor  // Lombok annotation to generate a constructor for required fields (final fields)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConfigMapWatcherService configMapWatcherService;

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        configMapWatcherService.startWatching();
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isRequestIdempotent(String transactionId) {
        return userRepository.existsByTransactionId(UUID.fromString(transactionId));
    }

    public UserEntity getUserByTransactionId(String transactionId) {
        return userRepository.findByTransactionId(UUID.fromString(transactionId));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public Optional<UserEntity> updateUser(UUID uniqueId, UserBOChanges changes) {
        return Optional.ofNullable(userRepository.findByUniqueId(uniqueId))
                .map(user -> {
                    if (isUserDataChanged(user, changes)) {
                        updateUserData(user, changes);
                        userRepository.save(user);
                        log.info("User with unique ID {} updated", uniqueId);
                    } else {
                        log.info("No changes detected for user with unique ID {}", uniqueId);
                    }
                    return user;
                });
    }

    private boolean isUserDataChanged(UserEntity user, UserBOChanges changes) {
        return !changes.getName().equals(user.getName()) ||
                !changes.getSurname().equals(user.getSurname()) ||
                !changes.getEmail().equals(user.getEmail()) ||
                !changes.getIndicators().equals(user.getIndicators());
    }

    private void updateUserData(UserEntity user, UserBOChanges changes) {
        user.setName(changes.getName());
        user.setSurname(changes.getSurname());
        user.setEmail(changes.getEmail());
        user.setIndicators(changes.getIndicators());
    }

    @Override
    public UserEntity getUserByUniqueId(UUID uniqueId) {
        return userRepository.findByUniqueId(uniqueId);
    }
}
