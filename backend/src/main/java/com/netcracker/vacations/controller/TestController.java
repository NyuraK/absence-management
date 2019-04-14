package com.netcracker.vacations.controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/user")
public class TestController {
    private List<Map<String, String>> users = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("users_id", "0"); put("login", "Anna"); put("password", "1234");
            put("role", "admin"); put("rest_days", "28"); put("hire_date", "01-02-2019");
            put("teams_id", "1"); put("name", "Анна"); put("surname", "Капранова");
            put("family_name", "Батькавна"); put("phone_number", "+79991112233");
            put("email", "anna@gmail.com"); put("description", "не знаю, зачем тут description");}
        });
        add(new HashMap<String, String>() {{
            put("users_id", "1"); put("login", "Artem"); put("password", "5678");
            put("role", "user"); put("rest_days", "28"); put("hire_date", "01-02-2019");
            put("teams_id", "1"); put("name", "Артем"); put("surname", "Роговский");
            put("family_name", "Батькавич"); put("phone_number", "+79991112233");
            put("email", "artem@mail.ru"); put("description", "не знаю, зачем тут description");}
        });
        add(new HashMap<String, String>() {{
            put("users_id", "2"); put("login", "Denis"); put("password", "1234");
            put("role", "manager"); put("rest_days", "28"); put("hire_date", "01-02-2019");
            put("teams_id", "1"); put("name", "Денис"); put("surname", "Мицковский");
            put("family_name", "Батькавич"); put("phone_number", "+79991112233");
            put("email", "denis@gmail.com"); put("description", "не знаю, зачем тут description");}
        });
    }};


    @GetMapping
    public List<Map<String, String>> users() {
        System.out.println("Get all users");
        return users;
    }

    @GetMapping("/{id}")
    public Map<String, String> getUser(@PathVariable String id) {
        System.out.println("Get one user");
        for (Map<String, String> user: users) {
            if(user.get("users_id").equals(id)){
                return user;
            }
        }
        return null;
//        return users.get(Integer.parseInt(id));
    }

    @PostMapping("/addUser")
    public Map<String, String> addUser(@RequestBody Map<String, String> newUser) {
        System.out.println("Add new user");
        users.add(newUser);
        return newUser;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        System.out.println("Delete user whith id: " + id);
        for (Map<String, String> user: users) {
            if(user.get("users_id").equals(id)){
                System.out.println("remove user whith id: " + user.get("users_id"));
                users.remove(user);
                break;
            }
        }
    }
    @PutMapping("{id}")
    public Map<String, String> updateUser(
            @PathVariable("id") String id,
            @RequestBody Map<String, String> newUser
    ) {
        System.out.printf("Update user");
        for (Map<String, String> user: users) {
            if(user.get("users_id").equals(id)){
                System.out.println("id удаляемого user" + user.get("users_id"));
                users.remove(user);
                break;
            }
        }
        System.out.println("id добавляемого user" + newUser.get("users_id"));
        users.add(newUser);

        return newUser;
    }
}
