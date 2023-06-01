package com.github.kylerequez.SpringBootUserRestApi.Services;

import com.github.kylerequez.SpringBootUserRestApi.Requests.LoginRequest;
import com.github.kylerequez.SpringBootUserRestApi.Requests.RegistrationRequest;
import com.github.kylerequez.SpringBootUserRestApi.Responses.LoginResponse;
import com.github.kylerequez.SpringBootUserRestApi.Responses.RegistrationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthServices {
    ResponseEntity<LoginResponse> loginRequest(LoginRequest request);
    ResponseEntity<RegistrationResponse> registrationRequest(RegistrationRequest request);
}
