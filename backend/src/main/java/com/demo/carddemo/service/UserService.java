package com.demo.carddemo.service;

import com.demo.carddemo.mapper.UserMapper;
import com.demo.carddemo.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getCurrentUser() {
        User user = userMapper.findCurrentUser();
        if (user != null) {
            user.setMonthlyProduction(7247.74);
            user.setF2fRate(2.0);
            user.setConsecutiveEliteMonths(12.0);
        }
        return user;
    }
}
