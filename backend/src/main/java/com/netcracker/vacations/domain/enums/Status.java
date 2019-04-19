package com.netcracker.vacations.domain.enums;

public enum Status {
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    CONSIDER("Under consideration");

    public final String name;

    Status(String name){
        this.name=name;
    }

    public static String[] getNames() {
        Status[] statuses = Status.values();
        String[] names = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            names[i] = statuses[i].name;
        }
        return names;
    }
    public String getName() {
        return name;
    }
}
