package com.netcracker.vacations.domain.enums;

public enum Status {
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    CONSIDER("Under consideration");

    public final String name;

    Status(String name){
        this.name=name;
    }

    public static Status byName(String name) {
        Status[] statuses = Status.values();
        for (Status status : statuses) {
            if (status.name.equals(name))
                return status;
        }
        return null;
    }

    public String getName() {
        return name;
    }

}
