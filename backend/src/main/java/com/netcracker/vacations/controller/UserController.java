package com.netcracker.vacations.controller;

import com.sun.tools.javac.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/secured")
public class UserController extends MyAbtractController {

    @GetMapping("/home")
    public ResponseEntity getPersonalView() {
        //probably here should return collection of days
        //to make a highlighted calendar
        return ResponseEntity.ok("Welcome!");
    }

    @PostMapping("/submit")
    public ResponseEntity submitRequest(String user, List<Date> range) {
        return ResponseEntity.ok("Data is saved");
    }

}
