package com.netcracker.vacations.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* Controller for testing security */
@RestController
@RequestMapping()
public class TempController {

    @GetMapping("/admin/home")
    public ResponseEntity getPersonalView() {
        return ResponseEntity.ok("Welcome, Admin!");
    }

    @GetMapping("/user/home")
    public ResponseEntity getPersonalViewUser() {
        return ResponseEntity.ok("Welcome, User!");
    }

    @GetMapping("/manager/home")
    public ResponseEntity submitRequest() {
        return ResponseEntity.ok("Welcome, Manager!");
    }


}
