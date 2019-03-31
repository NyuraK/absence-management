package com.netcracker.vacations;

import com.netcracker.vacations.security.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* Class represents temporary storage
* */
@Component
public class UserStorage {

    private final List<UserStorage.AppUser> users = Arrays.asList(
            new UserStorage.AppUser(1, "Artem", new BCryptPasswordEncoder().encode("5678"), Role.USER),
            new UserStorage.AppUser(2, "Anna", new BCryptPasswordEncoder().encode("1234"), Role.ADMIN)
    );

    public List<Role> getRole(String username) {
        for (AppUser user: users) {
            if (user.username.equals(username))
                return user.getRole();
        }
        return null;
    }

    public List<AppUser> getUsers() {
        return users;
    }

    // A (temporary) class represent the user saved in the database.
    public class AppUser {
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
    }

}
