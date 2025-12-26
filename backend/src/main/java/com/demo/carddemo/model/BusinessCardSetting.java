package com.demo.carddemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusinessCardSetting {
    private Long id;
    private Long cardId;
    private String settingKey;
    private Boolean isVisible;
    private LocalDateTime updatedAt;
}
