package com.netcracker.vacations.config;

import com.netcracker.vacations.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Arrays.asList("http://localhost:8088"));
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()
                .antMatchers("/onlyforadmin/**").hasAuthority("ADMIN")
                .antMatchers("/secured/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/login").permitAll()
                // Disallow everything else..
                .anyRequest().permitAll();

        // If a user try to access a resource without having enough permissions
//        http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
//        http.addFilterBefore(new JwtTokenFilter("login", jwtConfig), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new JwtAuthTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        // Optional, if you want to test the API from a browser
         http.httpBasic();
    }

    @Autowired
    private AppUserService appUserService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                    .cors()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/onlyforadmin/**").hasAuthority("ADMIN")
//                    .antMatchers("/secured/**").hasAnyAuthority("USER", "ADMIN")
//                    .antMatchers("/**").permitAll()
//                .and()
//                    .addFilterBefore(new JwtTokenFilter("/login", authenticationManager(), jwtConfig), UsernamePasswordAuthenticationFilter.class);
////                    .addFilterBefore(new JwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
