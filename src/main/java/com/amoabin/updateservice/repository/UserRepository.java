package com.amoabin.updateservice.repository;

import com.amoabin.updateservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email AND u.uniqueId = :uniqueId")
    long countByEmailAndUniqueId(@Param("email") String email, @Param("uniqueId") UUID uniqueId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.transactionId = :transactionId")
    boolean existsByTransactionId(@Param("transactionId") UUID transactionId);

    @Query("SELECT u FROM UserEntity u WHERE u.transactionId = :transactionId")
    UserEntity findByTransactionId(@Param("transactionId") UUID transactionId);

    @Query("SELECT u FROM UserEntity u WHERE u.uniqueId = :uniqueId")
    UserEntity findByUniqueId(UUID uniqueId);
}




