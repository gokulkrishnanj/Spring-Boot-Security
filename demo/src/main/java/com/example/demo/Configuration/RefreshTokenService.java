package com.example.demo.Configuration;

import com.example.demo.Exceptions.TokenExpiredException;
import com.example.demo.Models.Entity.RefreshToken;
import com.example.demo.Repository.RefreshTokenRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService{
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public RefreshToken GenerateRefreshToken(String userName,String token){
        RefreshToken refreshToken = new RefreshToken();
        Instant expiry = jwtService.extractExpiration(token).toInstant();
        expiry = expiry.plusSeconds(1200);
        refreshToken.setExpiry(Date.from(expiry));
//        System.out.println("refresh token expiry-->"+refreshToken.getExpiry());
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(userRepository.findByUserMail(userName).get());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> GetRefreshToken(String token){
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken isValid(RefreshToken refreshToken){
        System.out.println("refresh token expiry:"+ refreshToken.getExpiry());
        System.out.println("new date:"+ new Date());
        System.out.println("compare date:"+refreshToken.getExpiry().compareTo(new Date()));
        if((refreshToken.getExpiry().compareTo(new Date()))<0){
            refreshTokenRepository.delete(refreshToken);
            throw  new TokenExpiredException("Refresh Token Expired");
        }
        return  refreshToken;
    }

}
