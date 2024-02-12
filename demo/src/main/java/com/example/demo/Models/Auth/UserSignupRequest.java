package com.example.demo.Models.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
