package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.service.RequestService;
import com.netcracker.vacations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private RequestService reqService;

    @Autowired

    public RequestController(RequestService reqService, UserService userService) {
        this.reqService = reqService;
    }

    @PostMapping
    public void addRequest(@RequestBody RequestDTO request) {
        if (request.getNeedToEmail()) {
            reqService.sendMailRequest(request);
        }
        reqService.saveRequest(request);
    }

    @GetMapping("/active")
    public List<RequestDTO> getActiveRequests(@RequestParam String name) {
        return reqService.getActiveRequests(name);
    }

    @GetMapping("/resolved")
    public List<RequestDTO> getResolvedRequests(@RequestParam String name) {
        return reqService.getResolvedRequests(name);
    }

    @GetMapping("/managerVac")
    public boolean isManagerOnWork(@RequestParam String name) {
        return reqService.isManagerOnRest(name);
    }

    @GetMapping("/types")
    public String[] getTypes() {
        return RequestType.getNames();
    }

    @PatchMapping("/decline")
    public void declineRequest(@RequestBody List<Integer> requests) {
        reqService.updateRequest(Status.DECLINED, requests);
    }

    @PatchMapping("/approve")
    public void approveRequest(@RequestBody List<Integer> requests) {
        reqService.updateRequest(Status.ACCEPTED, requests);
    }

    //TODO implement weeks counting
    @GetMapping
    @ResponseBody
    public List<List<String>> getRequests() {
        return reqService.getRequests();
    }
}

