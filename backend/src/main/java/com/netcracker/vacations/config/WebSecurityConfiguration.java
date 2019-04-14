package com.netcracker.vacations.config;

import com.netcracker.vacations.domain.enums.Role;
import com.netcracker.vacations.service.AppUserService;
import com.netcracker.vacations.security.JwtLoginTokenFilter;
import com.netcracker.vacations.security.JwtTokenFilter;
import com.netcracker.vacations.security.JwtTokenProvider;
import com.netcracker.vacations.service.AppUserService;
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

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;

    private AppUserService appUserService;

    private JwtConfig jwtConfig;

    @Autowired
    public WebSecurityConfiguration(JwtTokenProvider jwtTokenProvider, AppUserService appUserService, JwtConfig jwtConfig) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.appUserService = appUserService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/**").hasAnyAuthority("ROLE_EMPLOYEE", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_DIRECTOR")
//                .antMatchers("/manager/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .addFilterBefore(new JwtLoginTokenFilter("/login", authenticationManagerBean(), jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
