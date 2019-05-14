package com.netcracker.vacations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.vacations.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtLoginTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authManager;

    private final JwtConfig jwtConfig;

    public JwtLoginTokenFilter(String url, AuthenticationManager authManager, JwtConfig jwtConfig) {
        super(new AntPathRequestMatcher(url));
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UserCredentials creds = null;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
        } catch (IOException e) {
            //TODO handle exception
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                creds.getUsername(), creds.getPassword());

        return authManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) {

        Long now = System.currentTimeMillis();

        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", authorities)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // jwtConfig.getExpiration() = 86400
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    }

    private static class UserCredentials {
        private String username, password;
        private Collection<GrantedAuthority> authorities;

        public String getUsername() {
            return username;
        }

        Collection<GrantedAuthority> getAuthorities() {
            return authorities;
        }

        void setAuthorities(Collection<GrantedAuthority> authorities) {
            this.authorities = authorities;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
