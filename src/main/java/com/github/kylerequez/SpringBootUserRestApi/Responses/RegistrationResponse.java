package com.github.kylerequez.SpringBootUserRestApi.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class RegistrationResponse {
    private HttpStatus status;
    private String message;
}
