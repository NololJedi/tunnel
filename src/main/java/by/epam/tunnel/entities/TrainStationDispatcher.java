package by.epam.tunnel.entities;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainStationDispatcher {

    private static final Logger LOGGER = Logger.getLogger(TrainStationDispatcher.class);
    private static TrainStationDispatcher instance = null;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private TrainStationDispatcher() {
    }

    public static TrainStationDispatcher getInstance() {

        if (isInstanceAvailable.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TrainStationDispatcher();
                    isInstanceAvailable.set(false);
                }
            } finally {
                lock.unlock();
            }
        }

        return instance;
    }

    public void observeTunnelForDirection(Tunnel tunnel, Train train) {

    }

    public void observeTunnelForTrainsCount(Tunnel tunnel, Train train) {
    }

}
