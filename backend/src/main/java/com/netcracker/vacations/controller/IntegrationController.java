package com.netcracker.vacations.controller;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.security.MyUserPrincipal;
import com.netcracker.vacations.service.IntegrationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/integration")
public class IntegrationController {

    private IntegrationService integrationService;

    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping
    public String getOAuth2Url() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = ((MyUserPrincipal) authentication.getPrincipal()).getUser();
        return integrationService.getRedirectURL(currentUser.getLogin());
    }

    @PutMapping
    public void cancelIntegration() {
        integrationService.cancelIntegration();
    }
}

