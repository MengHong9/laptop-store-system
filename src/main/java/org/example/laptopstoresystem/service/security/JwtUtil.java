package org.example.laptopstoresystem.service.security;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.laptopstoresystem.common.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Autowired
    private ApplicationConfig applicationConfig;


    private String secret;


    private long expiration;


    @PostConstruct
    private void init(){
        this.secret = applicationConfig.getSecurity().getSecret();
        this.expiration = applicationConfig.getSecurity().getExpiration();
    }


    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(this.secret.getBytes());
    }


    public String generateToken(UserDetails userDetails){
        Map<String , Object> claims = new HashMap<>();

        return this.createToken(claims , userDetails.getUsername());
    }


    private String createToken(Map<String , Object> claims , String subject){
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(this.getSigningKey() , SignatureAlgorithm.HS256)
                .compact();

        return token;
    }


    public String extractUsername(String token){
        return this.extractClaims(token , Claims::getSubject);
    }


    private <T> T extractClaims(String token , Function<Claims , T> claimResolver){
        Claims claims = this.extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Boolean validateToken(String token , UserDetails userDetails){
        Boolean isExpired = extractExpiration(token).before(new Date());
        String username = extractUsername(token);
        return !isExpired && username.equals(userDetails.getUsername());
    }

    public Date extractExpiration(String token){
        return extractClaims(token , Claims::getExpiration);
    }



}
