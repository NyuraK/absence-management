package com.netcracker.vacations.domain.enums;

public enum RequestsTypes {
    SICKNESS("Sick leave"),
    VACATION("Vacation"),
    CHILDCARE("Child care"),
    BUISNESSTRIP("Buisness trip"),
    REMOTE("Remote work");

    public final String name;

    RequestsTypes(String name){
        this.name=name;
    }
}
