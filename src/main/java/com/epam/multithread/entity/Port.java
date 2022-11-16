package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Logger logger = LogManager.getLogger();
    private static Port portInstance;
    private static Lock locker = new ReentrantLock();
    private int capacity;
    private int countContainers;

    private List<Pier> pierList = new ArrayList<>();


    public Port(int pierCount, int capacity) {
        this.capacity = capacity;
        this.countContainers = (int) (0.5 * capacity);
        for (int i = 1; i <= pierCount; i++) {
            this.pierList.add(new Pier(i));
        }
    }

    public synchronized static Port getInstance(int pierCount, int capacity) {
        if (portInstance == null) {
            locker.lock();
            if (portInstance == null) {
                portInstance = new Port(pierCount, capacity);

                logger.log(Level.INFO, "Port capacity is {}, actual size is {} containers",portInstance.capacity, portInstance.getCountContainers());
            }
            locker.unlock();
        }
        return portInstance;
    }

    public synchronized Pier getPier() {
        try {
            while (true) {
                for (Pier pier : this.pierList) {
                    if (pier.getStatus()) {

                        logger.log(Level.DEBUG, "Pier N{} is ready", pier.getPierNumber());

                        pier.setStatus(false);
                        return pier;
                    }
                }
                logger.log(Level.DEBUG, "All piers NOT free, wait");

                wait(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCountContainers() {
        return countContainers;
    }

    public void addContainers(int count) {
        this.countContainers += count;
    }

    public void removeContainers(int count) {
        this.countContainers -= count;
    }
}
