package com.github.kylerequez.SpringBootUserRestApi.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
