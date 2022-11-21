package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class Pier {
    private static Logger logger = LogManager.getLogger();
    private int pierNumber;

    public Pier() {
    }

    public Pier(int pierNumber) {
        this.pierNumber = pierNumber;
    }

    public int getPierNumber() {
        return pierNumber;
    }

    public void unload(Ship ship, Port port) {
        try {
            int countShip = ship.getCountContainer();
            int countPort = port.getCountContainers();
            if (countPort + countShip <= port.CAPACITY) {
                logger.log(Level.DEBUG, "{} get pier N {}", Thread.currentThread().getName(), this.pierNumber);
                TimeUnit.SECONDS.sleep(ship.getSize().getTimeSec());
                port.addContainers(countShip);
                ship.setCountContainer(0);   // Set ship EMPTY
                logger.log(Level.DEBUG, "{} was unloaded on pier N {}", Thread.currentThread().getName(), this.pierNumber);

            } else {
                logger.log(Level.DEBUG, "Port is FULL, wait 5 sec!");
                TimeUnit.SECONDS.sleep(5);
                port.removeContainers(port.CAPACITY / 2);
                logger.log(Level.DEBUG, "Port is ready");
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Pier caught exception {}" , e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    public void load(Ship ship, Port port) {
        try {
            int countShip = ship.getCountContainer();
            int countPort = port.getCountContainers();
            if (countPort >= countShip) {
                logger.log(Level.DEBUG, "{} get pier N {}", Thread.currentThread().getName(), this.pierNumber);
                TimeUnit.SECONDS.sleep(ship.getSize().getTimeSec());
                port.removeContainers(countShip);
                ship.setCountContainer(countShip);   // Set ship FULL
                logger.log(Level.DEBUG, "{} was loaded on pier N {}", Thread.currentThread().getName(), this.pierNumber);

            } else {
                logger.log(Level.WARN, "Port is EMPTY!");
                TimeUnit.SECONDS.sleep(5);
                logger.log(Level.INFO, "Port is ready");
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Pier caught exception {}" , e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
