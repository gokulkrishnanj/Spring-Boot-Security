package com.example.demo.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

//    private static final String SECRET_KEY = "qd50A1qT3iX7zP3w43l2s+zjYFrDRb7PatKle0zZbaNgOin+8k2/Q5rNVmcC5cvD";


    //method to generate token without extra claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    //method to generate token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24)))
                .signWith(generateSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //token validation(to validate whether the token belongs to correct user or not)
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //method to check whether the token is expired or not
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
//        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    // method() to get expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //method to extract username from token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //extract Single claim
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //get all the claims from token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(generateSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //creating signIn key to verify headers and payload(signature part)
    private Key generateSignInKey() {
        System.out.println("secretkey:" + SECRET_KEY);
        byte[] byteKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteKey);
    }
}

