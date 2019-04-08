package com.netcracker.vacations.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Secured("ROLE_ADMIN")
    @PostMapping("/addUser")
    public void addUser() {

    }

    @Secured("ROLE_ADMIN")
    public void changeRole() {

    }

}
