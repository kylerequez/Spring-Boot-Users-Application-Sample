package com.github.kylerequez.SpringBootUserRestApi.Services;

import com.github.kylerequez.SpringBootUserRestApi.ExceptionHandlers.Exceptions.UserNotFound;
import com.github.kylerequez.SpringBootUserRestApi.Models.UserPrincipal;
import com.github.kylerequez.SpringBootUserRestApi.Repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServicesImpl implements CustomeUserDetailServices{
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserNotFound {
        var user = this.usersRepository.findUserByEmail(username).orElseThrow(() -> new UserNotFound("User with email " + username + " does not exists."));
        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .password(user.getPassword())
                .build();
    }
}
