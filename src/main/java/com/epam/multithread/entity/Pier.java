package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Pier {
    private static Logger logger = LogManager.getLogger();
    private int pierNumber;
    private boolean pierIsFree = true;

    public Pier() {
    }

    public Pier(int pierNumber) {
        this.pierNumber = pierNumber;
    }

    public int getPierNumber() {
        return pierNumber;
    }

    public boolean getStatus() {
        return pierIsFree;
    }

    public void setStatus(boolean pierIsFree) {
        this.pierIsFree = pierIsFree;
    }

    public void unload(Ship ship, Port port) {
        try {
            int count = ship.getCountContainer();
            if (port.getCountContainers() + count <= port.getCapacity()) {

                logger.log(Level.DEBUG, "{} get pier N {}", Thread.currentThread().getName(), this.pierNumber);

                TimeUnit.SECONDS.sleep(ship.getSize().getTimeSec());
                port.addContainers(count);
                ship.setCountContainer(0);   // Set ship EMPTY
                this.setStatus(true);

                logger.log(Level.DEBUG, "{} was unloaded on pier N {}", Thread.currentThread().getName(), this.pierNumber);
                logger.log(Level.INFO, "Actual size port is {} containers", port.getCountContainers());
            } else {
                logger.log(Level.DEBUG, "Port is FULL, wait 5 sec!");

                TimeUnit.SECONDS.sleep(5);
                port.removeContainers(port.getCapacity() / 2);

                logger.log(Level.DEBUG, "Port is ready");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
