package com.belentpatrus.gasstation.model.dailysales.enums;

public enum Department {
    CIGARETTES(2),
    TABACCO(3),
    CONVENIENCE(4),
    DISPENSED_BEVERAGES(5),
    PHONE_CARDS(8),
    LOTTERY_AND_GAMING(9),
    EXEMPT_ZERO_RATED(10),
    OTHER(9999);
    private final int id;

    Department(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Department fromId(int id) {
        for (Department dept : values()) {
            if (dept.id == id) {
                return dept;
            }
        }
        System.out.println("Department not found: " + id);
        return OTHER;
    }
}
