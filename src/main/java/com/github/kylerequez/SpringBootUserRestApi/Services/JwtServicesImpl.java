package com.github.kylerequez.SpringBootUserRestApi.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.kylerequez.SpringBootUserRestApi.Config.JwtProperties;
import com.github.kylerequez.SpringBootUserRestApi.Models.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtServicesImpl implements JwtServices{
    private final JwtProperties jwtProperties;
    @Override
    public String issue(String id, String email, List<String> roles) {
        return JWT.create()
                .withSubject(id)
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", email)
                .withClaim("authorities", roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    @Override
    public DecodedJWT decode(String token) {
        return JWT
                .require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .build()
                .verify(token);
    }

    @Override
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .id(String.valueOf(jwt.getSubject()))
                .email(jwt.getClaim("email").asString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    @Override
    public List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("authorities");
        if(claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
