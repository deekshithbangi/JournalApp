package com.deekshith.journal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @RequestMapping("/health-check")
    public String healthCheck() {
        return "Health Check - Okay";
    }
}
