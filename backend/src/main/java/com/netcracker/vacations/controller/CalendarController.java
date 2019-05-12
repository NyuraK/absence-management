package com.netcracker.vacations.controller;

import com.netcracker.vacations.Util;
import com.netcracker.vacations.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private CalendarService service;

    @Autowired
    public CalendarController(CalendarService service) {
        this.service = service;
    }

    @GetMapping("/occupiedForDiscuss")
    public List<List<String>> getOccupiedDaysForDiscuss(HttpServletRequest request) {
        return service.getVacationsPerDay("Occupied", "Discuss", request);
    }

    @GetMapping("/busyForDiscuss")
    public List<List<String>> getBusyDaysForDiscuss(HttpServletRequest request) {
        return service.getVacationsPerDay("Busy", "Discuss", request);
    }

    @GetMapping("/occupiedForSend")
    public List<List<String>> getOccupiedDaysForSend(HttpServletRequest request) {
        return service.getVacationsPerDay("Occupied", "Send", request);
    }

    @GetMapping("/busyForSend")
    public List<List<String>> getBusyDaysForSend(HttpServletRequest request) {
        return service.getVacationsPerDay("Busy", "Send", request);
    }

    @GetMapping("/accepted")
    public List<String> getAccepted(HttpServletRequest request) {
        return service.getVacations("Accepted", request);
    }

    @GetMapping("/declined")
    public List<String> getDeclined(HttpServletRequest request) {
        String name = Util.extractLoginFromRequest(request);
        return service.getVacations("Declined", request);
    }

    @GetMapping("/consider")
    public List<String> getConsider(HttpServletRequest request) {
        return service.getVacations("Consider", request);
    }

}