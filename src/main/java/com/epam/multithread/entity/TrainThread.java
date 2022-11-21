package com.epam.multithread.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TrainThread extends Thread{
    private static Logger logger = LogManager.getLogger();



    @Override
    public void run() {
        Thread.currentThread().setName("Train");
        logger.log(Level.DEBUG, "{} was created", Thread.currentThread().getName());
        // logger.log(Level.DEBUG, "Thread is daemon {}", Thread.currentThread().isDaemon());
        Port port = Port.getInstance();
        port.managePort();
    }
}
