package com.github.kylerequez.SpringBootUserRestApi.Services;

import com.github.kylerequez.SpringBootUserRestApi.Models.User;
import com.github.kylerequez.SpringBootUserRestApi.Models.UserPrincipal;
import com.github.kylerequez.SpringBootUserRestApi.Models.VerificationLink;
import com.github.kylerequez.SpringBootUserRestApi.Repositories.UsersRepository;
import com.github.kylerequez.SpringBootUserRestApi.Repositories.VerificationLinksRepository;
import com.github.kylerequez.SpringBootUserRestApi.Requests.LoginRequest;
import com.github.kylerequez.SpringBootUserRestApi.Requests.RegistrationRequest;
import com.github.kylerequez.SpringBootUserRestApi.Responses.LoginResponse;
import com.github.kylerequez.SpringBootUserRestApi.Responses.RegistrationResponse;
import com.github.kylerequez.SpringBootUserRestApi.Responses.VerificationResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServicesImpl implements AuthServices {
    private final UsersRepository usersRepository;
    private final VerificationLinksRepository verificationLinksRepository;
    private final JwtServices jwtServices;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;
    public ResponseEntity<LoginResponse> loginRequest(LoginRequest request) {
        var user = this.usersRepository.findUserByEmail(request.getUsername());

        if(user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var entity = user.get();
        if(!passwordEncoder.matches(request.getPassword(), entity.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                            LoginResponse.builder()
                                    .status(HttpStatus.UNAUTHORIZED)
                                    .message("Incorrect password! Please try again.")
                                    .build()
            );
        }

        if(entity.getStatus().equals("UNREGISTERED")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    LoginResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED)
                            .message("Your account has yet to be confirmed. Please check your email to verify your email.")
                            .build()
            );
        }

        var authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var accessToken = this.jwtServices.issue(principal.getId(), principal.getUsername(), roles);

        return ResponseEntity.ok().body(
                        LoginResponse.builder()
                                .status(HttpStatus.OK)
                                .accessToken(accessToken)
                                .message("You have successfully logged in!")
                                .build()
        );
    }

    public ResponseEntity<RegistrationResponse> registrationRequest(RegistrationRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())) return ResponseEntity.status(HttpStatus.CONFLICT).body(
                RegistrationResponse.builder()
                        .status(HttpStatus.CONFLICT)
                        .message("Passwords must match! Please try again.")
                        .build()
        );

        if(this.usersRepository.findUserByContactNumberOrPassword(request.getContactNumber(), request.getPassword()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    RegistrationResponse.builder()
                            .status(HttpStatus.CONFLICT)
                            .message("Contact Number/Email is already being used! Please try again.")
                            .build()
            );

        var user = User.builder()
                .firstname(request.getFirstname())
                .middlename(request.getMiddlename())
                .lastname(request.getLastname())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .status("UNREGISTERED")
                .createdAt(new Date())
                .build();

        String link = null;
        try {
            this.usersRepository.save(user);

            var verification = VerificationLink.builder()
                    .user(user)
                    .build();

            var verificationEntity = this.verificationLinksRepository.save(verification);
            link = "http://localhost:8080/api/v1/auth/register/confirm/" + verificationEntity.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    RegistrationResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("There was an error in creating your account. Please try again.")
                            .build()
            );
        }

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Spring User Registration Verification Link");
            helper.setTo(user.getEmail());
            helper.setText("Here is your link to verify to the Spring User Application: " + link);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    RegistrationResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("There was an error in sending the verification link to your email. Please contact the administrator for further details.")
                            .build()
            );
        }

        return ResponseEntity.ok().body(
                RegistrationResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Please check your email to confirm your registration.")
                        .build()
        );
    }

    public ResponseEntity<VerificationResponse> verifyRegistration(String id) {
        var verification = this.verificationLinksRepository.findById(id);

        if(verification.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    VerificationResponse.builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message("The link was not found. Please try again.")
                            .build()
            );
        }

        var verificationEntity = verification.get();

        var user = this.usersRepository.findById(verificationEntity.getUser().getId());
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    VerificationResponse.builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message("The user was not found. Please try again.")
                            .build()
            );
        }

        this.verificationLinksRepository.delete(verificationEntity);
        var userEntity = user.get();
        userEntity.setStatus("REGISTERED");
        this.usersRepository.save(userEntity);

        return ResponseEntity.ok().body(
                VerificationResponse.builder()
                        .status(HttpStatus.OK)
                        .message("You have successfully verified your account. Please login.")
                        .build()
        );
    }
}
