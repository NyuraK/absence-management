package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.security.MyUserPrincipal;
import com.netcracker.vacations.service.IntegrationService2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/integration")
public class IntegrationController {

    private IntegrationService2 integrationService;

    public IntegrationController(IntegrationService2 integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping
    public String getOAuth2Url() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = ((MyUserPrincipal) authentication.getPrincipal()).getUser();
        System.out.println("логин из контекста:" + currentUser.getLogin());
        return integrationService.getRedirectURL(currentUser.getLogin());
    }

    @PostMapping
    public void insertEventAfterApproval() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(6);
        integrationService.insertEventsAfterApproval(list);
    }

    @PutMapping
    public void cancelIntegration() {
        integrationService.cancelIntegration();
    }
}

