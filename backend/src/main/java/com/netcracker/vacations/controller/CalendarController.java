package com.netcracker.vacations.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api")
public class CalendarController {

    @GetMapping("/vacations/{id}")
    public void getVacationDays() {

    }

    @GetMapping("/absence/{id}")
    public void getAbsenceDays() {

    }

}
