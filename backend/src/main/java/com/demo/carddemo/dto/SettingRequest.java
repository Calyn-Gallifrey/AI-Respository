package com.demo.carddemo.dto;

import lombok.Data;

@Data
public class SettingRequest {
    private String settingKey;
    private Boolean isVisible;
}
