package com.github.kylerequez.SpringBootUserRestApi.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "VerificationLink")
@Table(name = "user_verifications")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            nullable = false,
            unique = true
    )
    private String id;

    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;
}
