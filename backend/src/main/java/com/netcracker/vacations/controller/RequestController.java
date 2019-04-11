package com.netcracker.vacations.controller;

import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.dto.RestRequestDTO;
import com.netcracker.vacations.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RequestController {

    private RequestService service;

    @Autowired
    public RequestController(RequestService service) {
        this.service = service;
    }

    @PostMapping("/request/add")
    public void addRequest(@RequestBody RequestDTO request) {
        service.saveRequest(request);
    }

    @GetMapping("/requests")
    public List<RestRequestDTO> getRequests() {
        return service.getRequests();
    }

}
