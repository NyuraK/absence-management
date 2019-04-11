package com.netcracker.vacations.domain.enums;

public enum Status {
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    CONSIDER("Under consideration");

    public final String name;

    Status(String name){
        this.name=name;
    }
}
