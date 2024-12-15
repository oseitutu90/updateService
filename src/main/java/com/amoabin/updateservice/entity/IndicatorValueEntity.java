package com.amoabin.updateservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "indicator_values", schema = "user_management")
public class IndicatorValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_classification", nullable = false, unique = true)
    private String accountClassification;

    @Column(name = "unique_id", nullable = false, unique = true)
    private UUID uniqueId;

    @Column(name = "indicator", columnDefinition = "json")
    private String indicator;

    // Constructors, getters, setters, and other fields as needed.
}
