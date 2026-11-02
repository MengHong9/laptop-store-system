package org.example.laptopstoresystem.service.security;

import org.example.laptopstoresystem.dto.auth.AuthDto;
import org.example.laptopstoresystem.dto.auth.AuthResponseDto;
import org.example.laptopstoresystem.dto.auth.RefreshTokenDto;
import org.example.laptopstoresystem.dto.auth.RefreshTokenResponseDto;
import org.example.laptopstoresystem.dto.user.UserDto;
import org.example.laptopstoresystem.entity.RefreshToken;
import org.example.laptopstoresystem.entity.User;
import org.example.laptopstoresystem.exception.model.CustomAuthenticationException;
import org.example.laptopstoresystem.exception.model.DuplicateException;
import org.example.laptopstoresystem.mapper.UserMapper;
import org.example.laptopstoresystem.repository.RefreshTokenRepository;
import org.example.laptopstoresystem.repository.UserRepository;
import org.example.laptopstoresystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserService userService;


    @Autowired
    private RefreshTokenService refreshTokenService;




    public AuthResponseDto register(UserDto dto){
        if (userRepository.existsByName(dto.getName())) {
            throw new DuplicateException("user already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateException("email already exists");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);

        String accessToken = jwtUtil.generateToken(createdUser);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(createdUser);


        return new AuthResponseDto(accessToken , refreshToken.getToken());
    }


    public AuthResponseDto login(AuthDto payload){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername() , payload.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(payload.getUsername());

        String accessToken = jwtUtil.generateToken(userDetails);


        User user =(User) userDetails;
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponseDto(accessToken , refreshToken.getToken());
    }

    public RefreshTokenResponseDto refreshToken(RefreshTokenDto dto) {
        String token = dto.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(token);

        try {
            refreshTokenService.verifyToken(refreshToken);
        }catch (AuthenticationException e){
            throw new CustomAuthenticationException("Invalid refresh token");
        }



        // get user from refresh token
        User user = refreshToken.getUser();


        // generate new access token and store in DB
        String newAccessToken = jwtUtil.generateToken(user);

        // rotate refresh token
        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken);

        return new RefreshTokenResponseDto(newAccessToken , newRefreshToken.getToken() , "Bearer");
    }

}
