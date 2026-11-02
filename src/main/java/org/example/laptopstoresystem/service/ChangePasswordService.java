package org.example.laptopstoresystem.service;

import org.example.laptopstoresystem.dto.user.ChangePasswordDto;
import org.example.laptopstoresystem.entity.User;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChangePasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePasswordUser(Long id, ChangePasswordDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        // compare against the hash using the encoder, never with .equals()
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("old password is incorrect");
        }

        if (!Objects.equals(dto.getNewPassword(), dto.getConfirmPassword())) {
            throw new RuntimeException("new password and confirm password does not match");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}
