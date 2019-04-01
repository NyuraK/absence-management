package com.netcracker.vacations.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.http.HttpServletRequest;

/*Class for redirecting all requests to index.html view.
*It is at developing stage */

//@Controller
public class UniversalController {
    //{path:(?:(?!api|.).)*}/**
    @RequestMapping(value = "{path:[^\\.]*}")
    public View check(HttpServletRequest request) {
        return new InternalResourceView("/public/index.html");
    }
}
