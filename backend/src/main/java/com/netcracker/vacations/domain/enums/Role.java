package com.netcracker.vacations.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN("Administrator"),
    DIRECTOR("Director"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");

    private String name;


    Role(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Role findByName(String value) {
        for (Role role : Role.values()) {
            if (role.name.equals(value)) return role;
        }
        return null;
    }

    @JsonValue
    public String getName() {
        return name;
    }


}
