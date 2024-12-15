package com.amoabin.updateservice.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserBO {
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String transactionId;
    private String indicators;
    private String uniqueId;
    private String createdAt;
    private String updatedAt;

}
