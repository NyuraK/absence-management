package com.netcracker.vacations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VacationsApplication {

//    private final RequestService service;

//    @Autowired
//    public VacationsApplication(RequestService service) {
//        this.service = service;
//    }

    public static void main(String[] args) {
        SpringApplication.run(VacationsApplication.class, args);
    }

}
