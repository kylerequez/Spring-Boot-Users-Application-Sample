package com.github.kylerequez.SpringBootUserRestApi.Services;

import java.util.List;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.kylerequez.SpringBootUserRestApi.Models.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface JwtServices {
    String issue(String id, String email, List<String> roles);
    DecodedJWT decode(String token);
    UserPrincipal convert(DecodedJWT jwt);
    List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt);
}
