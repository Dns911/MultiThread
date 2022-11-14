package com.epam.multithread;

import com.epam.multithread.entity.Ship;
import com.epam.multithread.entity.Size;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainDemo {
    private static Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        logger.log(Level.DEBUG, Runtime.getRuntime().availableProcessors());
        Ship sh1 = new Ship(Size.LARGE, 0);
        Ship sh2 = new Ship(Size.MEDIUM, 20);
        Ship sh3 = new Ship(Size.SMALL, 0);
        ArrayList<Ship> qqq = new ArrayList<>();
        qqq.add(sh1);
        qqq.add(sh2);
        qqq.add(sh3);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        List<Future<Ship>> sr = service.invokeAll(qqq);
        service.shutdown();

    }
}
