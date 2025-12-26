package com.demo.carddemo.controller;

import com.demo.carddemo.dto.ApiResponse;
import com.demo.carddemo.dto.SettingRequest;
import com.demo.carddemo.dto.SettingsPayload;
import com.demo.carddemo.model.BusinessCard;
import com.demo.carddemo.model.BusinessCardSetting;
import com.demo.carddemo.service.BusinessCardService;
import com.demo.carddemo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BusinessCardController {
    private final BusinessCardService businessCardService;
    private final UserService userService;

    public BusinessCardController(BusinessCardService businessCardService, UserService userService) {
        this.businessCardService = businessCardService;
        this.userService = userService;
    }

    @GetMapping("/cards")
    public ApiResponse<List<BusinessCard>> listCards() {
        Long userId = userService.getCurrentUser().getId();
        return ApiResponse.ok(businessCardService.findCardsForUser(userId));
    }

    @GetMapping("/cards/{cardId}/settings")
    public ApiResponse<Map<String, Boolean>> getSettings(@PathVariable Long cardId) {
        return ApiResponse.ok(businessCardService.getSettingsForCard(cardId));
    }

    @PutMapping("/cards/{cardId}/settings")
    public ApiResponse<Map<String, Boolean>> saveSettings(@PathVariable Long cardId, @RequestBody SettingsPayload payload) {
        BusinessCard card = businessCardService.findById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }
        List<BusinessCardSetting> settings = payload.getSettings().stream().map(req -> {
            BusinessCardSetting s = new BusinessCardSetting();
            s.setSettingKey(req.getSettingKey());
            s.setIsVisible(req.getIsVisible());
            return s;
        }).collect(Collectors.toList());
        Map<String, Boolean> updated = businessCardService.saveSettings(cardId, settings);
        return ApiResponse.ok(updated);
    }
}
