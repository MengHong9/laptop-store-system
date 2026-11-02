package org.example.laptopstoresystem.controller;



import jakarta.validation.Valid;
import org.example.laptopstoresystem.common.response.Response;
import org.example.laptopstoresystem.dto.user.ChangePasswordDto;
import org.example.laptopstoresystem.dto.user.UserDto;
import org.example.laptopstoresystem.dto.user.UserResponseDto;
import org.example.laptopstoresystem.dto.user.UserUpdateDto;
import org.example.laptopstoresystem.entity.User;
import org.example.laptopstoresystem.service.ChangePasswordService;
import org.example.laptopstoresystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChangePasswordService changePasswordService;


    @GetMapping
    public ResponseEntity<Response> getUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved data", users));
    }





    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@Valid @RequestBody UserUpdateDto dto , @PathVariable Long id){
        userService.updateUser(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully updated data"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully deleted data"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id){
        UserResponseDto users = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved data", users));
    }


    @GetMapping("/search")
    public ResponseEntity<Response> getUserByUsername(@RequestParam String username){
        List<UserResponseDto> users = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved data", users));
    }

    @PatchMapping("/change_password/{id}")
    public ResponseEntity<Response> changePassword(@PathVariable Long id , @Valid @RequestBody ChangePasswordDto dto){
        changePasswordService.changePasswordUser(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully updated password"));
    }
}
