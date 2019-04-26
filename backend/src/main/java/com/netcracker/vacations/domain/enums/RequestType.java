package com.netcracker.vacations.domain.enums;


public enum RequestType {
    SICKNESS("Sick leave"),
    VACATION("Vacation"),
    CHILDCARE("Child care"),
    BUSINESSTRIP("Business trip"),
    REMOTE("Remote work");

    public final String name;

    RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RequestType byName(String name) {
        RequestType[] types = RequestType.values();
        for (RequestType type : types) {
            if (type.name.equals(name))
                return type;
        }
        return null;
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
