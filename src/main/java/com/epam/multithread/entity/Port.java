package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Port {
    private static Logger logger = LogManager.getLogger();
    private static Port portInstance;
    private List<Ship> shipList;
    private int pierCount;
    private static final int maxShipsInPort = portInstance.pierCount;
    private static final int minShipsInPort = 0;
    private int shipCounter = 0;


    public Port(int pierCount) {
        this.pierCount = pierCount;
        shipList = new ArrayList<>();
    }

    public synchronized static Port getInstance(int pierCount) {
        if (portInstance == null) {
            portInstance = new Port(pierCount);
        }
        return portInstance;
    }



    public boolean add(Ship ship) {
        try {
            if (shipCounter < maxShipsInPort) {
                notifyAll();
                shipList.add(ship);
                logger.log(Level.DEBUG, "ship arrive to port" + shipList.size() + Thread.currentThread().getName());
                shipCounter++;
            } else {
                logger.log(Level.DEBUG, "No place for ship" + Thread.currentThread().getName());
                wait();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log(Level.DEBUG, "shipCounter");
        return true;
    }


}
