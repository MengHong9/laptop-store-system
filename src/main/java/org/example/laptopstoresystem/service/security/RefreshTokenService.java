package org.example.laptopstoresystem.service.security;


import jakarta.annotation.PostConstruct;
import org.example.laptopstoresystem.common.config.ApplicationConfig;
import org.example.laptopstoresystem.entity.RefreshToken;
import org.example.laptopstoresystem.entity.User;
import org.example.laptopstoresystem.exception.model.CustomAuthenticationException;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;


@Service
public class RefreshTokenService {

    private long expiration;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    @PostConstruct
    private void init(){
        this.expiration = applicationConfig.getSecurity().getRefreshTokenExpiration();
    }


    public RefreshToken createRefreshToken(User user){
        String refreshToken = generateSecureRefreshToken();

        RefreshToken entity = new RefreshToken();
        entity.setToken(refreshToken);
        entity.setExpiresAt(LocalDateTime.now().plusHours(expiration));
        entity.setUser(user);

        return refreshTokenRepository.save(entity);
    }


    private String generateSecureRefreshToken(){
        SecureRandom random = new SecureRandom();

        byte[] tokenBytes = new byte[64];

        random.nextBytes(tokenBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public RefreshToken findByToken(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->new ResourceNotFoundException("Invalid refresh token"));
    }

    public RefreshToken verifyToken(RefreshToken token) throws AuthenticationException {
        // expiration && revoke
        if (!token.isValid()){
            refreshTokenRepository.delete(token);
            throw new CustomAuthenticationException("refresh token was expired");
        }

        return token;

    }


    public RefreshToken rotateRefreshToken(RefreshToken oldToken){
        // revoked old token
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        // generate new refresh token
        return createRefreshToken(oldToken.getUser());
    }


    public void deleteExpiredAndRevokeTokens(){
        refreshTokenRepository.deleteExpiredAndRevokeTokens(LocalDateTime.now());
    }

}
