package org.example.laptopstoresystem.service;



import org.example.laptopstoresystem.dto.auth.AuthDto;
import org.example.laptopstoresystem.dto.user.ChangePasswordDto;
import org.example.laptopstoresystem.dto.user.UserResponseDto;
import org.example.laptopstoresystem.dto.user.UserUpdateDto;
import org.example.laptopstoresystem.entity.User;
import org.example.laptopstoresystem.exception.model.ResourceNotFoundException;
import org.example.laptopstoresystem.mapper.UserMapper;
import org.example.laptopstoresystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;




    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }




    public void updateUser(Long id , UserUpdateDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        userMapper.updateUser(dto, user);
        userRepository.save(user);
    }


    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        userRepository.delete(user);
    }


    public UserResponseDto getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return userMapper.toDto(user);
    }


    public List<UserResponseDto> getUserByUsername(String username){
        List<User> user = userRepository.findByNameContainsIgnoreCase(username);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("user not found");
        }

        return userMapper.toDtoList(user);
    }


    public void changePasswordUser(Long id , ChangePasswordDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));


        if(!Objects.equals(user.getPassword() , dto.getOldPassword())){
            throw new RuntimeException("old password is incorrect");
        }


        if (!Objects.equals(dto.getNewPassword() , dto.getConfirmPassword())){
            throw new RuntimeException("new password and confirm password does not match");
        }

        userMapper.changeUserPassword(user , dto.getNewPassword());
        userRepository.save(user);
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(()-> {
                    throw new UsernameNotFoundException("username not found" + username);
                });
    }




    public void login(AuthDto payload){

    }
}