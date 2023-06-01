package com.github.kylerequez.SpringBootUserRestApi.Models;

public record UserDTO(
        String id,
        String role,
        String firstname,
        String middlename,
        String lastname,
        String contactNumber,
        String email
) {
}
