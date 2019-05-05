package com.netcracker.vacations.controller;

import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<UserDTO> users(@RequestParam(name = "teamId", required = false) Integer teamId,
                               @RequestParam(name = "managerId", required = false) Integer managerId) {
        if (teamId != null) {
            return service.getUsersFromTeam(teamId);
        } else if (managerId != null) {
            return service.getUsersSubordinateToManager(managerId);
        }
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Integer id) {
        return service.getUser(id);
    }


    @GetMapping("/userByCode/{code}")
    public String getUserByCode(@PathVariable("code") String code) {
        return service.getUserByCode(code);
    }

    @PatchMapping("/changePassword")
    public void changePassword(@RequestBody List<String> userInfo) {
        service.changePassword(userInfo);
    }

    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        userDTO = service.sendMailPassword(userDTO);
        return service.addUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserDTO userDTO
    ) {
        return service.updateUser(id, userDTO);
    }

    @PutMapping("/password/{id}")
    public void updatePassword(
            @PathVariable("id") Integer id,
            @RequestBody String password
    ) {
        service.updatePassword(id, password);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        service.deleteUser(id);
    }

}
