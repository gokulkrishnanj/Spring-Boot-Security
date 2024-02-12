package com.example.demo.Controller;

import com.example.demo.API.HealthAPI;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthAPI {
    public String healthCheck() {
        return "Health";
    }
}
