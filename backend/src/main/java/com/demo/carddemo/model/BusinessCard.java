package com.demo.carddemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusinessCard {
    private Long id;
    private Long userId;
    private String cardType;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
