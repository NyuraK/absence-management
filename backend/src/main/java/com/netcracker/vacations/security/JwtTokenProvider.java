package com.netcracker.vacations.security;

import com.netcracker.vacations.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider{
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtTokenProvider(JwtConfig jwtSettings) {
        this.jwtConfig = jwtSettings;
    }

//    @Override
//    public Authentication authenticate(Authentication auth) throws AuthenticationException {
//        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) auth.getCredentials();
//        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtConfig.getSecret());
//        String subject = jwsClaims.getBody().getSubject();
//        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
//        List<GrantedAuthority> authorities = scopes.stream()
//                .map(authority -> new SimpleGrantedAuthority(authority))
//                .collect(Collectors.toList());
//
////        UserContext confitext = UserContext.create(subject, authorities);
//
////        return new JwtAuthenticationToken(context, context.getAuthorities());
//        return auth;
//    }

    public String createToken(String username, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtConfig.getExpiration());

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())//
                .compact();
    }

//    @Override
//    public boolean supports(Class<?> aClass) {
//        return false;
//    }
}
