package com.epam.multithread;

import com.epam.multithread.entity.Ship;
import com.epam.multithread.entity.Size;
import com.epam.multithread.entity.TrainThread;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainDemo {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        logger.log(Level.INFO, Runtime.getRuntime().availableProcessors());

        List<Ship> shipList = List.of(new Ship(Size.LARGE, false), new Ship(Size.MEDIUM, false),
                new Ship(Size.SMALL, false), new Ship(Size.MEDIUM, false),
                new Ship(Size.SMALL, false), new Ship(Size.LARGE, true));
        TrainThread daemonTrain = new TrainThread();
        int i = 1;
        for (Ship ship : shipList) {
            ship.setShipName("SHIP " + i);
            i++;
        }
        daemonTrain.setDaemon(true);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        shipList.forEach(ship -> service.execute(ship));
        daemonTrain.start();

        service.shutdown();
    }
}
