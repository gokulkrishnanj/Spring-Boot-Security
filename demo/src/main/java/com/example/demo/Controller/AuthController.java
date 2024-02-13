package com.example.demo.Controller;

import com.example.demo.API.AuthAPI;
import com.example.demo.Models.Auth.AuthenticationResponse;
import com.example.demo.Models.Auth.RequestRefreshToken;
import com.example.demo.Models.Auth.UserLoginRequest;
import com.example.demo.Models.Auth.UserSignupRequest;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthAPI {
    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<AuthenticationResponse> signUpUser(UserSignupRequest userSignupRequest) {
        return new ResponseEntity<>(authService.signUpUser(userSignupRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(UserLoginRequest userLoginRequest) {
        return new ResponseEntity<>(authService.loginUser(userLoginRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> getAccessTokenUsingRefreshToken(RequestRefreshToken requestRefreshToken){
        return new ResponseEntity<>(authService.getAccessTokenUsingRefreshToken(requestRefreshToken),HttpStatus.OK);
    }
}
