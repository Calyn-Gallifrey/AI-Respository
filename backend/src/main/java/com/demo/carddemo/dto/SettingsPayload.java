package com.demo.carddemo.dto;

import lombok.Data;
import java.util.List;

@Data
public class SettingsPayload {
    private List<SettingRequest> settings;
}
