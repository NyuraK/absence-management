package com.netcracker.vacations.controller;

import com.netcracker.vacations.service.IntegrationService2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("callback")
public class IntegrationCallbackController {

    private IntegrationService2 integrationService;

    public IntegrationCallbackController(IntegrationService2 integrationService) {
        this.integrationService = integrationService;
    }


    @GetMapping
    public RedirectView callback(@RequestParam(name = "code") String code) throws Exception {
        integrationService.insertEvents(code);
        return new RedirectView("https://calendar.google.com/calendar/r");
    }
}

