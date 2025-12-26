package com.demo.carddemo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SettingKeys {
    public static final List<String> ORDERED_KEYS = Arrays.asList(
            "tenure",
            "professional_title",
            "service_introduction",
            "honor_medals",
            "customer_comments",
            "total_customers",
            "total_claims",
            "policies_in_force",
            "total_coverage",
            "premium_services",
            "company_profile",
            "agent_highlights"
    );

    public static final Set<String> KEY_SET = ORDERED_KEYS.stream().collect(Collectors.toSet());
}
