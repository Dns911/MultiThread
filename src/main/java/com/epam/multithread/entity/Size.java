package com.epam.multithread.entity;

public enum Size {
    SMALL(10, 1),
    MEDIUM(20, 2),
    LARGE(50, 5);

    private int value;
    private int timeMillis;

    Size(int value, int timeSec) {
        this.value = value;
        this.timeMillis = timeSec;
    }

    public int getValue() {
        return value;
    }
    public int getTimeSec(){ return timeMillis;}
}
