package com.netcracker.vacations.controller;

import com.netcracker.vacations.Util;
import com.netcracker.vacations.dto.UserDTO;
import com.netcracker.vacations.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private static final Logger logger = LogManager.getLogger(UsersController.class);


    private UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Integer id) {
        return service.getUser(id);
    }

    @GetMapping("/userByCode/{code}")
    public String getUserByCode(@PathVariable("code") String code) {
        return service.getUserByCode(code);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
//        userDTO = service.sendMailPassword(userDTO);
        return service.addUser(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/changePassword")
    public void changePassword(@RequestBody List<String> userInfo) {
        service.changePassword(userInfo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDTO updateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserDTO userDTO
    ) {
        return service.updateUser(id, userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/password/{id}")
    public void updatePassword(
            @PathVariable("id") Integer id,
            @RequestBody String password
    ) {
        service.updatePassword(id, password);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        service.deleteUser(id);
    }

    @PreAuthorize("@Security.isAllowed(#username)")
    @GetMapping("/team")
    public ResponseEntity<?> getUserTeam(@RequestParam @P("username") String username) {
        return ResponseEntity.accepted().body(service.getUserTeam(username));
    }

    @GetMapping("/restdays")
    public ResponseEntity<?> getRestDays(HttpServletRequest request) {
        String username = Util.extractLoginFromRequest(request);
        return ResponseEntity.ok(service.getRestDays(username));
    }

}
