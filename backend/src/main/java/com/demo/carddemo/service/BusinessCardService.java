package com.demo.carddemo.service;

import com.demo.carddemo.mapper.BusinessCardMapper;
import com.demo.carddemo.mapper.BusinessCardSettingMapper;
import com.demo.carddemo.model.BusinessCard;
import com.demo.carddemo.model.BusinessCardSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusinessCardService {
    private final BusinessCardMapper businessCardMapper;
    private final BusinessCardSettingMapper settingMapper;

    public BusinessCardService(BusinessCardMapper businessCardMapper, BusinessCardSettingMapper settingMapper) {
        this.businessCardMapper = businessCardMapper;
        this.settingMapper = settingMapper;
    }

    public List<BusinessCard> findCardsForUser(Long userId) {
        return businessCardMapper.findByUserId(userId);
    }

    public BusinessCard findById(Long id) {
        return businessCardMapper.findById(id);
    }

    public Map<String, Boolean> getSettingsForCard(Long cardId) {
        List<BusinessCardSetting> settings = settingMapper.findByCardId(cardId);
        Map<String, Boolean> map = new HashMap<>();
        for (BusinessCardSetting setting : settings) {
            map.put(setting.getSettingKey(), Boolean.TRUE.equals(setting.getIsVisible()));
        }
        for (String key : SettingKeys.ORDERED_KEYS) {
            map.putIfAbsent(key, false);
        }
        return map;
    }

    @Transactional
    public Map<String, Boolean> saveSettings(Long cardId, List<BusinessCardSetting> updatedSettings) {
        for (BusinessCardSetting setting : updatedSettings) {
            if (!SettingKeys.KEY_SET.contains(setting.getSettingKey())) {
                throw new IllegalArgumentException("Invalid setting key: " + setting.getSettingKey());
            }
            BusinessCardSetting existing = settingMapper.findByCardIdAndKey(cardId, setting.getSettingKey());
            setting.setCardId(cardId);
            setting.setUpdatedAt(LocalDateTime.now());
            if (existing == null) {
                settingMapper.insertSetting(setting);
            } else {
                settingMapper.updateSetting(setting);
            }
        }
        return getSettingsForCard(cardId);
    }
}
