package com.example.demo.Service.Impl;

import com.example.demo.Configuration.JWTService;
import com.example.demo.Configuration.RefreshTokenService;
import com.example.demo.Exceptions.TokenNotFoundException;
import com.example.demo.Models.Auth.AuthenticationResponse;
import com.example.demo.Models.Auth.RequestRefreshToken;
import com.example.demo.Models.Auth.UserLoginRequest;
import com.example.demo.Models.Auth.UserSignupRequest;
import com.example.demo.Models.Entity.RefreshToken;
import com.example.demo.Models.Entity.Role;
import com.example.demo.Models.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public AuthenticationResponse signUpUser(UserSignupRequest userSignupRequest) {
        User user = new User();
        user.setFirstName(userSignupRequest.getFirstName());
        user.setLastName(userSignupRequest.getLastName());
        user.setUserMail(userSignupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userSignupRequest.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(token);
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse loginUser(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(authentication.isAuthenticated()){
            var user = userRepository.findByUserMail(userLoginRequest.getEmail()).orElseThrow();
            var token = jwtService.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.GenerateRefreshToken(userLoginRequest.getEmail(), token);
            authenticationResponse.setAccessToken(token);
            authenticationResponse.setRefreshToken(refreshToken.getRefreshToken());
        }
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse getAccessTokenUsingRefreshToken(RequestRefreshToken requestRefreshToken){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.GetRefreshToken(requestRefreshToken.getRefreshToken());
        if(refreshTokenOptional.isPresent()){
            RefreshToken refreshToken = refreshTokenService.isValid(refreshTokenOptional.get());
            String accessToken = jwtService.generateToken(refreshToken.getUser());
            authenticationResponse.setAccessToken(accessToken);
            authenticationResponse.setRefreshToken(requestRefreshToken.getRefreshToken());
        }
        else {
            throw new TokenNotFoundException("token not found");
        }
        return authenticationResponse;
    }
    }
