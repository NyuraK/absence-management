package com.netcracker.vacations.controller;

import com.netcracker.vacations.Util;
import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<RequestDTO> getActiveRequests(HttpServletRequest request) {
        String username = Util.extractLoginFromRequest(request);
        return reqService.getActiveRequests(username);
    }

    @GetMapping("/resolved")
    public List<RequestDTO> getResolvedRequests(HttpServletRequest request) {
        String username = Util.extractLoginFromRequest(request);
        return reqService.getResolvedRequests(username);
    }

    @GetMapping("/managerVac")
    public boolean isManagerOnWork(HttpServletRequest request) {
        return reqService.isManagerOnRest(request);
    }

    @GetMapping("/types")
    public String[] getTypes() {
        return RequestType.getNames();
    }

    @PatchMapping("/decline")
    public void declineRequest(@RequestBody List<Integer> requests, HttpServletRequest request) {
        String username = Util.extractLoginFromRequest(request);
        reqService.updateRequest(Status.DECLINED, requests, username);
    }

    @PatchMapping("/approve")
    public void approveRequest(@RequestBody List<Integer> requests, HttpServletRequest request) {
        String username = Util.extractLoginFromRequest(request);
        reqService.updateRequest(Status.ACCEPTED, requests, username);
    }

}

