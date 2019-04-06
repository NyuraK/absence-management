package com.netcracker.vacations;

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
            new UserStorage.AppUser(1, "Artem", new BCryptPasswordEncoder().encode("5678"), "USER"),
            new UserStorage.AppUser(2, "Anna", new BCryptPasswordEncoder().encode("1234"), "ADMIN"),
            new UserStorage.AppUser(3, "Denis", new BCryptPasswordEncoder().encode("0987"), "MANAGER")
            );

    public List<AppUser> getUsers() {
        return users;
    }

    // A (temporary) class represent the user saved in the database.
    public class AppUser {
        private Integer id;
        private String username, password;
        private String role;

        public AppUser(Integer id, String username, String password, String role) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
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

        public String getRole() {
            return role;
        }

    }

}
