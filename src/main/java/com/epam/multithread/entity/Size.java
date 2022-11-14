package com.epam.multithread.entity;

public enum Size {
    SMALL(10),
    MEDIUM(20),
    LARGE(50);

    private int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
