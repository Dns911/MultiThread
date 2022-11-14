package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class Ship implements Callable<Ship> {
    private static Logger logger = LogManager.getLogger();
    private  Size shipSize;
    private  int countContainer;

    public Ship(Size shipSize, int countContainer) {
        this.shipSize = shipSize;
        this.countContainer = countContainer;
    }

    public Size getSize() {
        return shipSize;
    }

    public void load(int count){
        if (this.countContainer + count <= this.shipSize.getValue()){
            this.countContainer += count;
        } else {
            this.countContainer = shipSize.getValue();
        }
    }

    public void unload(int count){
        if (this.countContainer - count >= 0){
            this.countContainer -= count;
        } else {
            this.countContainer = 0;
        }
    }

    @Override
    public Ship call() throws Exception {

        logger.log(Level.DEBUG, "ship {} arrive to Port", Thread.currentThread().getName());
        Port port = Port.getInstance(2);
        port.add(this);
        logger.log(Level.DEBUG, "Port {}", port.toString());





        return this;
    }
}