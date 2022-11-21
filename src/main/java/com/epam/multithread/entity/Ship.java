package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship implements Runnable {
    private static Logger logger = LogManager.getLogger();
    private Size shipSize;
    private int countContainer;
    private String shipName;
    private boolean isEmptyShip;

    public Ship(Size shipSize, boolean isEmptyShip) {
        this.shipSize = shipSize;
        this.isEmptyShip = isEmptyShip;
        if (isEmptyShip){
            this.countContainer = 0;
        } else {
            this.countContainer = shipSize.getValue();
        }
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
        Port port = Port.getInstance();
        Pier pier = port.getPier();
        if (this.isEmptyShip){
            pier.load(this, port);
        } else {
            pier.unload(this, port);
        }
        port.realisePier(pier);


        logger.log(Level.DEBUG, "{} departure from Port", Thread.currentThread().getName());
        logger.log(Level.INFO, "Actual size port is {} containers", port.getCountContainers());

    }
}