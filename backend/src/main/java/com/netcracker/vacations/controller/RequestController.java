package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.service.CalendarService;
import com.netcracker.vacations.service.MethodsService;
import com.netcracker.vacations.domain.enums.RequestType;
import com.netcracker.vacations.domain.enums.Status;
import com.netcracker.vacations.dto.RequestDTO;
import com.netcracker.vacations.dto.RestRequestDTO;
import com.netcracker.vacations.repository.*;
import com.netcracker.vacations.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.netcracker.vacations.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private RequestService service;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RequestTypeRepository typeRepo;
    @Autowired
    private RequestRepository reqRepo;
    @Autowired
    private DepartmentRepository depRepo;
    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    public RequestController(RequestService service) {
        this.service = service;
        System.out.println("used method ReqController");
    }

    @PostMapping
    public void addRequest(@RequestBody RequestDTO request) {
        service.saveRequest(request);
        System.out.println("used method add");
    }

    @GetMapping
    public List<RestRequestDTO> getRequests() {
        System.out.println("used method getreq");
        CalendarService methods =new CalendarService(userRepo, teamRepo, depRepo, reqRepo, typeRepo);
        methods.getVacations("Accepted", "Denis");
        return service.getRequests();

    }

    @GetMapping("/types")
    public String[] getTypes() {
        System.out.println("used method gettypes");

        //methods.getUsersAcceptedRequests(2,"Both","Child care","Under consideration");
        return RequestType.getNames();
    }

    @GetMapping("/statuses")
    public String[] getStatuses() {
        System.out.println("used method getstatuses");
        return Status.getNames();
    }

}
