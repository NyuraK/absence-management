package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.repository.UserRepository;
import com.netcracker.vacations.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private CalendarService service;

    @Autowired
    public CalendarController(CalendarService service) {
        this.service = service;
    }

    @Autowired
    private UserRepository userRepo;

    @GetMapping()
    public List<String> getReqDates(@RequestBody String name) {
        return new ArrayList<String>();
    }

    @GetMapping("/occupied")
    public List<String> getOccupiedDays(@RequestParam String name) {
        return service.getVacationsPerDay("Occupied", name);
    }

    @GetMapping("/busy")
    public List<String> getBusyDays(@RequestParam String name) {
        return service.getVacationsPerDay("Busy", name);
    }

    @GetMapping("/accepted")
    public List<String> getAccepted(@RequestParam String name) {
        return service.getVacations("Accepted", name);
    }

    @GetMapping("/declined")
    public List<String> getDeclined(@RequestParam String name) {
        return service.getVacations("Declined", name);
    }

    @GetMapping("/consider")
    public List<String> getConsider(@RequestParam String name) {
        return service.getVacations("Consider", name);
    }
}