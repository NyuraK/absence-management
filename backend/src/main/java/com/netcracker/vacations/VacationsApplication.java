package com.netcracker.vacations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class VacationsApplication {
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
    }
    public static void main(String[] args) {
        SpringApplication.run(VacationsApplication.class, args);
    }

}
