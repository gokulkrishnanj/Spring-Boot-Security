package com.example.demo.API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "${version}")
public interface HealthAPI {
    @GetMapping(value = "${health}")
    String healthCheck();
}
