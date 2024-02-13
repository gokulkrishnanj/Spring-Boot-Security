package com.example.demo.Service;

import com.example.demo.Models.Auth.AuthenticationResponse;
import com.example.demo.Models.Auth.RequestRefreshToken;
import com.example.demo.Models.Auth.UserLoginRequest;
import com.example.demo.Models.Auth.UserSignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthenticationResponse signUpUser(UserSignupRequest userSignupRequest);

    AuthenticationResponse loginUser(UserLoginRequest userLoginRequest);

    AuthenticationResponse getAccessTokenUsingRefreshToken(RequestRefreshToken requestRefreshToken);
}
