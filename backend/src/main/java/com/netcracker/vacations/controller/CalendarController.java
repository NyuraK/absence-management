package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.repository.UserRepository;
import com.netcracker.vacations.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/occupied")
    public List<String> getOccupiedDays(String name){
        UserEntity user=userRepo.findByLogin(name).get(0);
        return service.getVacationsPerDay("Occupied",user);
    }
    @GetMapping("/busy")
    public List<String> getBusyDays(String name){
        UserEntity user=userRepo.findByLogin(name).get(0);
        return service.getVacationsPerDay("Busy",user);
    }
}