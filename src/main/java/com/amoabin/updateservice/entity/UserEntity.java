package com.amoabin.updateservice.entity;

import com.amoabin.updateservice.service.util.Indicator;
import jakarta.persistence.*;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "user_management")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "transaction_id")
    private UUID transactionId;


    @Column(name = "indicators", columnDefinition = "jsonb")
//    @Convert(converter = JsonListToDbJsonConverter.class)
    private String indicators;



    @Column(name = "unique_id")
    private UUID uniqueId;


    public UserEntity() {
        this.uniqueId = UUID.randomUUID();
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;



    public UserEntity(Long user_id, String name, String surname, String email, UUID transactionId , UUID uniqueId,
                      Date createdAt, Date updatedAt, String indicators) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.transactionId = transactionId;
        this.indicators = indicators;
        this.uniqueId = uniqueId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }


    public String getIndicators() {
        return indicators;
    }

    public void setIndicators(String indicators) {
        this.indicators = indicators;
    }
    public UUID getUniqueId() {
        return uniqueId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        transactionId = UUID.randomUUID();
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        transactionId = UUID.randomUUID();
        updatedAt = new Date();
    }


}
