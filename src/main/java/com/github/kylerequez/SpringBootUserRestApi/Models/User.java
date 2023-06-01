package com.github.kylerequez.SpringBootUserRestApi.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
          unique = true,
          nullable = false
    )
    private String id;

    @Column(
            unique = false,
            nullable = true
    )
    private String role;
    @Column(
            unique = false,
            nullable = false
    )
    private String firstname;

    @Column(
            unique = false,
            nullable = false
    )
    private String middlename;

    @Column(
            unique = false,
            nullable = false
    )
    private String lastname;

    @Column(
            unique = false,
            nullable = false
    )
    private String contactNumber;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    @Column(
            nullable = false
    )
    private String password;

    @Column(
            nullable = false
    )
    private String status;

    @Column(
            nullable = false
    )
    private Date createdAt;
}
