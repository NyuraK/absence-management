package com.netcracker.vacations;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class LoginController {

    private List<Map<String, String>> users = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("login", "Anna"); put("password", "1234"); }});
        add(new HashMap<String, String>() {{ put("login", "Artem"); put("password", "5678"); }});
        add(new HashMap<String, String>() {{ put("login", "Denis"); put("password", "9012"); }});
    }};

    @PostMapping("/login")
    public void check(@RequestBody Map<String, String> credentials) {
        System.out.println("profit? " + users.contains(credentials));
    }


}
