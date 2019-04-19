package com.netcracker.vacations.domain.enums;

public enum Role {
    ADMIN("Admin"),
    DIRECTOR("Director"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");

    public final String name;

    Role(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
