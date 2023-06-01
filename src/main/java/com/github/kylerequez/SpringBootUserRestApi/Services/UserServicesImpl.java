package com.github.kylerequez.SpringBootUserRestApi.Services;

import com.github.kylerequez.SpringBootUserRestApi.ExceptionHandlers.Exceptions.UserNotFound;
import com.github.kylerequez.SpringBootUserRestApi.Models.User;
import com.github.kylerequez.SpringBootUserRestApi.Models.UserDTO;
import com.github.kylerequez.SpringBootUserRestApi.Repositories.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        var users = this.usersRepository.findAll()
                .stream()
                .map(entity -> new UserDTO(
                        entity.getId(),
                        entity.getRole(),
                        entity.getFirstname(),
                        entity.getMiddlename(),
                        entity.getLastname(),
                        entity.getContactNumber(),
                        entity.getEmail()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(String id) {
        var user = this.usersRepository.findById(id);
        if(user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var entity = user.get();

        return ResponseEntity.ok(
            new UserDTO(
                    entity.getId(),
                    entity.getRole(),
                    entity.getFirstname(),
                    entity.getMiddlename(),
                    entity.getLastname(),
                    entity.getContactNumber(),
                    entity.getEmail()
            )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<String> insertUser(User user) {
        var existingUser = this.usersRepository.findUserByContactNumberOrEmail(
                user.getContactNumber(),
                user.getEmail()
        );

        if(existingUser.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed in inserting user. User already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        var savedUser = this.usersRepository.save(user);
        return ResponseEntity.ok().body("Saved user " + new UserDTO(
                savedUser.getId(),
                savedUser.getRole(),
                savedUser.getFirstname(),
                savedUser.getMiddlename(),
                savedUser.getLastname(),
                savedUser.getContactNumber(),
                savedUser.getEmail()
        ));
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateUser(String id, User user) {
        var existingUser = this.usersRepository.findUserById(id);

        if(existingUser.isEmpty()) throw new UserNotFound("Failed in updating. User with id " + id + " does not exists.");

        var updatedUser = existingUser.get();
        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setMiddlename(user.getMiddlename());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setContactNumber(user.getContactNumber());
        updatedUser.setEmail(user.getEmail());

        this.usersRepository.save(updatedUser);
        return ResponseEntity.ok().body("User with id " + id + " has been updated.");
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUser(String id) {
        var user = this.usersRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFound("Failed in deleting. User with id " + id + " does not exists.");
        this.usersRepository.deleteById(id);
        return ResponseEntity.ok().body("User with id " + id + " has been deleted.");
    }
}
