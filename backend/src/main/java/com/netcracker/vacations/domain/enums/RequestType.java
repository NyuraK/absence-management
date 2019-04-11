package com.netcracker.vacations.domain.enums;

public enum RequestType {
    SICKNESS("Sick leave"),
    VACATION("Vacation"),
    CHILDCARE("Child care"),
    BUSINESSTRIP("Business trip"),
    REMOTE("Remote work");

    public final String name;

    RequestType(String name){
        this.name=name;
    }
}
