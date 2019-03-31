package com.netcracker.vacations.controller;

//import com.netcracker.vacations.security.MyUserService;

import com.netcracker.vacations.security.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {

    private List<Map<String, String>> users = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("login", "Anna");
            put("password", "1234");
        }});
        add(new HashMap<String, String>() {{
            put("login", "Artem");
            put("password", "5678");
        }});
        add(new HashMap<String, String>() {{
            put("login", "Denis");
            put("password", "9012");
        }});
    }};

//    @PostMapping("/signin")
//    public String check(@RequestParam String login, @RequestParam String password) {
//        System.out.println(login + " " + password);
//        return appUserService.signin(login, password);
//    }

    public String check(HttpServletRequest request) {
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
        System.out.println("\n\nrequest " + request.getUserPrincipal().getName());
        return "";
    }
}
