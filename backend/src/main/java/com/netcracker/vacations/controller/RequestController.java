package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.dto.RestRequestDTO;
import com.netcracker.vacations.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private RequestService service;

    @Autowired
    public RequestController(RequestService service) {
        this.service = service;
    }

    @PostMapping
    public void addRequest(@RequestBody RequestDTO request) {
        service.saveRequest(request);
    }

    @GetMapping
    public List<RestRequestDTO> getRequests() {
        return service.getRequests();
    }

    @GetMapping("/types")
    public String[] getTypes() {
        return RequestType.getNames();
    }

}
