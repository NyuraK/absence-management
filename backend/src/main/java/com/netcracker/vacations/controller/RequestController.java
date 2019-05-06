package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.service.RequestService;
import com.netcracker.vacations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO request) {
        reqService.saveRequest(request);
        if (request.getNeedToEmail()) {
            reqService.sendMailRequest(request);
        }
        return ResponseEntity.ok("Requests is sent");
    }

    @PreAuthorize("@Security.isTeamMember(#name, null)")
    @GetMapping("/active")
    public List<RequestDTO> getActiveRequests(@RequestParam String name) {
        return reqService.getActiveRequests(name);
    }

    @PreAuthorize("@Security.isTeamMember(#name, null)")
    @GetMapping("/resolved")
    public List<RequestDTO> getResolvedRequests(@RequestParam @P("name") String name) {
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

    //TODO should add logic on the backend to decline only my requests?
    @PreAuthorize("@Security.isTeamMember(#name, null)")
    @PatchMapping("/decline")
    public void declineRequest(@RequestBody List<Integer> requests, @RequestParam @P("name") String name) {
        reqService.updateRequest(Status.DECLINED, requests);
    }

    @PreAuthorize("@Security.isTeamMember(#name, null)")
    @PatchMapping("/approve")
    public void approveRequest(@RequestBody List<Integer> requests, @RequestParam @P("name") String name) {
        reqService.updateRequest(Status.ACCEPTED, requests);
    }

}

