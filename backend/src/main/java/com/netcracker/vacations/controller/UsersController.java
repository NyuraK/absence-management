package com.netcracker.vacations.controller;

import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public List<UserDTO> users() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Integer id) {
        return service.getUser(id);
    }

    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        return service.addUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserDTO userDTO
    ) {
        return service.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        service.deleteUser(id);
    }

}
