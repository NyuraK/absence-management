package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.security.SecurityExpressionMethods;
import com.netcracker.vacations.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private RequestService reqService;

    @Autowired
    public RequestController(RequestService reqService) {
        this.reqService = reqService;
    }

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO request) {
        reqService.saveRequest(request);
        if (request.getNeedToEmail()) {
            reqService.sendMailRequest(request);
        }
        return ResponseEntity.ok("Requests is sent");
    }

    @GetMapping("/active")
    public List<RequestDTO> getActiveRequests() {
        String username = SecurityExpressionMethods.currentUserLogin();
        return reqService.getActiveRequests(username);
    }

    @GetMapping("/resolved")
    public List<RequestDTO> getResolvedRequests() {
        String username = SecurityExpressionMethods.currentUserLogin();
        return reqService.getResolvedRequests(username);
    }

    @GetMapping("/managerVac")
    public boolean isManagerOnWork() {
        return reqService.isManagerOnRest();
    }

    @GetMapping("/types")
    public String[] getTypes() {
        return RequestType.getNames();
    }

    @PatchMapping("/decline")
    public void declineRequest(@RequestBody List<Integer> requests) {
        String username = SecurityExpressionMethods.currentUserLogin();
        reqService.updateRequest(Status.DECLINED, requests, username);
    }

    @PatchMapping("/approve")
    public void approveRequest(@RequestBody List<Integer> requests) {
        String username = SecurityExpressionMethods.currentUserLogin();
        reqService.updateRequest(Status.ACCEPTED, requests, username);
    }

}

