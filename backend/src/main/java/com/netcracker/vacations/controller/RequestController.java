package com.netcracker.vacations.controller;

import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RequestController {

    private RequestService service;

    @Autowired
    public RequestController(RequestService service) {
        this.service = service;
    }

    @GetMapping("/calendar")
    public ResponseEntity getCalendar() {
        return ResponseEntity.ok("calendar!");
    }

    @GetMapping("/admin/home")
    public ResponseEntity getPersonalView() {
        return ResponseEntity.ok("Welcome, Admin!!");
    }

    @GetMapping("/user/home")
    public ResponseEntity getPersonalViewUser() {
        return ResponseEntity.ok("Welcome, User!");
    }

    @GetMapping("/manager/home")
    public ResponseEntity submitRequest() {
        return ResponseEntity.ok("Welcome, Manager!");
    }

    @PostMapping("/request/add")
    public void addRequest(@RequestBody RequestDTO request) {
//        System.out.println(request.getDescription() + " " + request.getStart() + " " +
//                " " + request.getEnd() + " "
//                + request.getType()) ;
        service.saveRequest(request);
    }

}
