package com.netcracker.vacations;

import com.netcracker.vacations.exception.MyAuthenticationException;
import com.netcracker.vacations.security.JwtTokenProvider;
import com.netcracker.vacations.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    // hard coding the users. All passwords must be encoded.
    private final List<AppUser> users = Arrays.asList(
            new AppUser(1, "Artem", encoder.encode("5678"), Role.ROLE_USER),
            new AppUser(2, "Anna", encoder.encode("1234"), Role.ROLE_ADMIN)
    );


    public List<Role> getRole(String username) {
        for (AppUser user: users) {
            if (user.username.equals(username))
                return user.getRole();
        }
        return null;
    }

    // A (temporary) class represent the user saved in the database.
    public static class AppUser {
        private Integer id;
        private String username, password;
        private List<Role> roles = new ArrayList<>();

        public AppUser(Integer id, String username, String password, Role role) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.roles.add(role);
        }

        public String getUsername() {
            return username;
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

        public List<Role> getRole() {
            return roles;
        }

        public void setRole(Role role) {
            this.roles.add(role);
        }
// getters and setters ....
    }


    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, getRole(username));
        } catch (AuthenticationException e) {
            throw new MyAuthenticationException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        for(AppUser appUser: users) {
            if(appUser.getUsername().equals(username)) {

                // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
                // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());

                // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
                // And used by auth manager to verify and check user authentication.
                return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
            }
        }

        // If user not found. Throw this exception.
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

}
