package com.netcracker.vacations.domain.enums;

public enum Statuses {
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    CONSIDER("Under consideration");

    public final String name;

    Statuses (String name){
        this.name=name;
    }
}
