package com.netcracker.vacations.domain.enums;

public enum Role {
    ADMIN("Administrator"),
    DIRECTOR("Director"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");

    public final String name;

    Role(String name){
        this.name=name;
    }
}
