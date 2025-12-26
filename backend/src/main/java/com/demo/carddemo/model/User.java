package com.demo.carddemo.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String role;
    private String branch;
    private String avatarUrl;
    private Double monthlyProduction;
    private Double f2fRate;
    private Double consecutiveEliteMonths;
}
