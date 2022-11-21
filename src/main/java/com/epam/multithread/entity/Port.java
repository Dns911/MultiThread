package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Logger logger = LogManager.getLogger();
    private static Port portInstance;
    private static Lock locker = new ReentrantLock();
    private final int PIER_COUNT = 2;
    private int countContainers;
    private Queue<Pier> pierList = new ArrayDeque<>();
    public final int CAPACITY = 500;


    public Port() {
        this.countContainers = (int) (0.5 * CAPACITY);
        for (int i = 1; i <= PIER_COUNT; i++) {
            this.pierList.add(new Pier(i));
        }
    }

    public static Port getInstance() {
        if (portInstance == null) {
            locker.lock();
            if (portInstance == null) {
                portInstance = new Port();
                logger.log(Level.INFO, "Port capacity is {}, actual size is {} containers",
                        portInstance.CAPACITY, portInstance.getCountContainers());
            }
            locker.unlock();
        }
        return portInstance;
    }

    public synchronized Pier getPier() {
        try {
            if (this.pierList.size() == 0) {
                logger.log(Level.WARN, "All piers NOT free, wait");
                wait();
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Exception {}" , e.getMessage());
            Thread.currentThread().interrupt();
        }
        Pier pier = this.pierList.remove();
        logger.log(Level.INFO, "Pier N{} is ready", pier.getPierNumber());
        return pier;
    }

    public synchronized void realisePier(Pier pier) {
        try {
            this.pierList.add(pier);
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Port caught exception {}" , e.getMessage());
            Thread.currentThread().interrupt();
        }
        notify();
    }

    public synchronized void managePort() {
        try {
            int sizeTrain = (int) (this.CAPACITY * 0.25);
            int counter = 0;
            while (counter < 6) {
                if (this.getCountContainers() < this.CAPACITY * 0.75 && this.getCountContainers() > this.CAPACITY * 0.25) {
                    logger.log(Level.INFO, "Train waiting...");
                    wait();
                } else if (this.getCountContainers() >= this.CAPACITY * 0.75) {
                    this.removeContainers(sizeTrain);
                    logger.log(Level.INFO, "Train decrease port size 25%");
                } else {
                    this.addContainers(sizeTrain);
                    logger.log(Level.INFO, "Train increase port size 25%");
                }
                counter++;
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Port caught exception {}" , e.getMessage());
            Thread.currentThread().interrupt();
        }

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
