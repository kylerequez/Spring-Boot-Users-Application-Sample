package com.github.kylerequez.SpringBootUserRestApi.Controllers;

import com.github.kylerequez.SpringBootUserRestApi.Requests.LoginRequest;
import com.github.kylerequez.SpringBootUserRestApi.Requests.RegistrationRequest;
import com.github.kylerequez.SpringBootUserRestApi.Responses.LoginResponse;
import com.github.kylerequez.SpringBootUserRestApi.Responses.RegistrationResponse;
import com.github.kylerequez.SpringBootUserRestApi.Responses.VerificationResponse;
import com.github.kylerequez.SpringBootUserRestApi.Services.AuthServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServicesImpl authServices;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginRequest(@RequestBody LoginRequest request) {
        return this.authServices.loginRequest(request);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registrationRequest(@RequestBody RegistrationRequest request) {
        return this.authServices.registrationRequest(request);
    }

    @GetMapping("/register/confirm/{id}")
    public ResponseEntity<VerificationResponse> registrationConfirmation(@PathVariable String id) {
        return this.authServices.verifyRegistration(id);
    }
}
