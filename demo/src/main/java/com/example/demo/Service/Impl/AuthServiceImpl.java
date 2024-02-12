package com.example.demo.Service.Impl;

import com.example.demo.Configuration.JWTService;
import com.example.demo.Models.Auth.AuthenticationResponse;
import com.example.demo.Models.Auth.UserLoginRequest;
import com.example.demo.Models.Auth.UserSignupRequest;
import com.example.demo.Models.Entity.Role;
import com.example.demo.Models.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        authenticationResponse.setToken(token);
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse loginUser(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
        var user = userRepository.findByUserMail(userLoginRequest.getEmail()).orElseThrow();
        System.out.println("userMail:" + user.getUserMail());
        var token = jwtService.generateToken(user);
        System.out.println("logintoken-->" + token);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        return authenticationResponse;
    }
}
