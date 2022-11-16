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
        logger.log(Level.INFO, Runtime.getRuntime().availableProcessors());

        List<Ship> shipList = List.of(new Ship(Size.LARGE), new Ship(Size.MEDIUM), new Ship(Size.SMALL),
                new Ship(Size.MEDIUM), new Ship(Size.SMALL), new Ship(Size.LARGE));
        int i = 1;
        for (Ship ship : shipList) {
            ship.setShipName("SHIP " + i);
            i++;
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        shipList.forEach(ship -> service.execute(ship));
        service.shutdown();
    }
}
