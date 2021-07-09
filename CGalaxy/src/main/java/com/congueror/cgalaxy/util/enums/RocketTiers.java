package com.congueror.cgalaxy.util.enums;

public enum RocketTiers {
    TIER1("tier_1", 1000),
    CREATIVE("creative", 1000000000)
    ;

    String name;
    int capacity;

    RocketTiers(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return "rocket_" + name;
    }

    public int getCapacity() {
        return capacity;
    }
}
