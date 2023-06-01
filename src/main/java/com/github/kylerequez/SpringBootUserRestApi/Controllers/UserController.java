package com.github.kylerequez.SpringBootUserRestApi.Controllers;

import com.github.kylerequez.SpringBootUserRestApi.Models.User;
import com.github.kylerequez.SpringBootUserRestApi.Models.UserDTO;
import com.github.kylerequez.SpringBootUserRestApi.Services.UserServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServicesImpl userServices;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return this.userServices.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id){
        return this.userServices.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> insertUser(@RequestBody User user) {
        return this.userServices.insertUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatedUser(@PathVariable String id, @RequestBody User user) {
        return this.userServices.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        return this.userServices.deleteUser(id);
    }
}
