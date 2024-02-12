package com.example.demo.API;

import com.example.demo.Models.Auth.AuthenticationResponse;
import com.example.demo.Models.Auth.UserLoginRequest;
import com.example.demo.Models.Auth.UserSignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/auth/api/v1")
public interface AuthAPI {
    @PostMapping(value = "/signUp")
    ResponseEntity<AuthenticationResponse> signUpUser(@RequestBody UserSignupRequest userSignupRequest);

    @PostMapping(value = "/login")
    ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest);

}
