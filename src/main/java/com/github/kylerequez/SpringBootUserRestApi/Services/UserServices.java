package com.github.kylerequez.SpringBootUserRestApi.Services;

import com.github.kylerequez.SpringBootUserRestApi.Models.UserDTO;
import org.springframework.http.ResponseEntity;
import com.github.kylerequez.SpringBootUserRestApi.Models.User;

import java.util.List;

public interface UserServices {
    ResponseEntity<List<UserDTO>> getAllUsers();
    ResponseEntity<UserDTO> getUserById(String id);
    ResponseEntity<String> insertUser(User user);
    ResponseEntity<String> updateUser(String id, User user);
    ResponseEntity<String> deleteUser(String id);
}
