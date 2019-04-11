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

    public static RequestType findByName(String name) {
        RequestType[] types = RequestType.values();
        for (RequestType type : types) {
            if (type.name.equals(name))
                return type;
        }
        return null;
    }
}
