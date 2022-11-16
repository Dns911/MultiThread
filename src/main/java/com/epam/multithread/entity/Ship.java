package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship implements Runnable {
    private static Logger logger = LogManager.getLogger();
    private Size shipSize;
    private int countContainer;
    private String shipName;

    public Ship(Size shipSize) {
        this.shipSize = shipSize;
        this.countContainer = shipSize.getValue();
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Size getSize() {
        return shipSize;
    }

    public int getCountContainer() {
        return countContainer;
    }

    public void setCountContainer(int countContainer) {
        this.countContainer = countContainer;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.getShipName());
        logger.log(Level.DEBUG, "{} was created", Thread.currentThread().getName());
        Port port = Port.getInstance(2, 1000);
        Pier pier = port.getPier();
        pier.unload(this, port);
        logger.log(Level.DEBUG, "{} leave from Port", Thread.currentThread().getName());
    }
}