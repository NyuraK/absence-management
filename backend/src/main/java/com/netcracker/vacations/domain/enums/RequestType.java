package com.netcracker.vacations.domain.enums;


import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestType {
    SICKNESS("Sick leave"),
    VACATION("Vacation"),
    CHILDCARE("Child care"),
    BUSINESSTRIP("Business trip"),
    REMOTE("Remote work");

    private final String name;

    RequestType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static String[] getNames() {
        RequestType[] types = RequestType.values();
        String[] names = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            names[i] = types[i].name;
        }
        return names;
    }
}
