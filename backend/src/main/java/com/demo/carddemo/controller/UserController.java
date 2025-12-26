package com.demo.carddemo.controller;

import com.demo.carddemo.dto.ApiResponse;
import com.demo.carddemo.model.User;
import com.demo.carddemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ApiResponse<User> getCurrentUser() {
        return ApiResponse.ok(userService.getCurrentUser());
    }
}
