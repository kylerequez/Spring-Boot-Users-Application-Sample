package com.github.kylerequez.SpringBootUserRestApi.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RegistrationRequest {
    private String firstname;
    private String middlename;
    private String lastname;
    private String contactNumber;
    private String email;
    private String password;
    private String confirmPassword;
}
