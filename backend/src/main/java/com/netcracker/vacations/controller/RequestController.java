package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
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

    @GetMapping("/active")
    public List<RequestDTO> getActiveRequests() {
        return service.getActiveRequests();
    }

    @GetMapping("/resolved")
    public List<RequestDTO> getResolvedRequests() {
        return service.getResolvedRequests();
    }

    @GetMapping("/types")
    public String[] getTypes() {
        return RequestType.getNames();
    }

    @PatchMapping("/decline")
    public void declineRequest(@RequestBody List<Integer> requests) {
        service.updateRequest(Status.DECLINED, requests);
    }

    @PatchMapping("/approve")
    public void approveRequest(@RequestBody List<Integer> requests) {
        service.updateRequest(Status.ACCEPTED, requests);
    }

    //TODO implement weeks counting
    @GetMapping
    @ResponseBody
    public List<List<String>> getRequests(){
        return service.getRequests();
    }

}
