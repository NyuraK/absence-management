package com.netcracker.vacations.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    CONSIDER("Under consideration");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
