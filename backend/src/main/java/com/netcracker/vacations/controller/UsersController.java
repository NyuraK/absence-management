package com.netcracker.vacations.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

//  работа с пользователями
//    private final UsersRepository usersRepository;
//    public UsersController(UsersRepository usersRepository){
//        this.usersRepository = usersRepository;
//    }
//
//    @Secured("ROLE_ADMIN")
//    @GetMapping
//    public List<UsersEntity> users() {
//        return usersRepository.findAll();
//    }
//
//    @Secured("ROLE_ADMIN")
//    @GetMapping("{id}")
//    public UsersEntity getUser(@PathVariable("id") UsersEntity user) {
//        return user;
//    }
//
//    @Secured("ROLE_ADMIN")
//    @PostMapping("/addUser")
//    public UsersEntity addUser(@RequestBody UsersEntity user) {
//        return usersRepository.save(user);
//    }
//
//    @Secured("ROLE_ADMIN")
//    @PutMapping("{id}")
//    public UsersEntit updateUser(
//            @PathVariable("id") UsersEntit userFromDb,
//            @RequestBody UsersEntit user
//    ) {
//        BeanUtils.copyProperties(user, userFromDb, "id");
//
//        return usersRepository.save(userFromDb);
//    }
//
//    @Secured("ROLE_ADMIN")
//    @DeleteMapping("{id}")
//    public void deleteUser(@PathVariable("id") UsersEntit user) {
//        usersRepository.delete(user);
//    }

}
