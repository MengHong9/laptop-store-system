package org.example.laptopstoresystem.mapper;


import org.example.laptopstoresystem.dto.user.ChangePasswordDto;
import org.example.laptopstoresystem.dto.user.UserDto;
import org.example.laptopstoresystem.dto.user.UserResponseDto;
import org.example.laptopstoresystem.dto.user.UserUpdateDto;
import org.example.laptopstoresystem.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        return user;
    }


    public UserResponseDto toDto(User entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setCreated_at(entity.getCreated_at());
        dto.setUpdated_at(entity.getUpdated_at());

        return dto;
    }

    public List<UserResponseDto> toDtoList(List<User> entity) {
        if (entity == null || entity.isEmpty()) {
            return new ArrayList<>();
        }
        return entity.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }


    public void updateUser(UserUpdateDto dto , User entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
    }


    public void changeUserPassword(User user , String newPassword){
        user.setPassword(newPassword);
    }
}
