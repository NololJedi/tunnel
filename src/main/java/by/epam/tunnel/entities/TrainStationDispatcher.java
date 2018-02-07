package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainDroveOutTunnelState;
import by.epam.tunnel.entities.trainstates.TrainDroveInTunnelState;
import by.epam.tunnel.entities.trainstates.TrainNearTunnelState;
import by.epam.tunnel.entities.trainstates.TrainState;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainStationDispatcher {

    private final static int TUNNEL_INDEX = 10;

    private static TrainStationDispatcher instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private static Lock lock = new ReentrantLock();
    private final Condition everestAvailable = lock.newCondition();
    private final Condition killerAvailable = lock.newCondition();

    private Tunnel tunnelEverest = new Tunnel("Everest", 15, 40, 1);
    private Tunnel tunnelKiller = new Tunnel("Killer", 100, 150, 3);

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

    public void observeTunnels(Train train, int currentTrainDistance) {
        observeTunnel(train, currentTrainDistance, tunnelEverest, everestAvailable);
        observeTunnel(train, currentTrainDistance, tunnelKiller, killerAvailable);
    }

    private void observeTunnel(Train train, int currentTrainDistance, Tunnel tunnel, Condition condition) {
        int startTunnelPoint = tunnel.getTunnelStartPoint();
        int finishTunnelPoint = tunnel.getTunnelFinishPoint();

        if (startTunnelPoint == currentTrainDistance + TUNNEL_INDEX) {
            TrainState trainNearTunnelState = new TrainNearTunnelState(train, tunnel);
            train.setTrainState(trainNearTunnelState);
        }

        lock.lock();
        try {
            if (startTunnelPoint == currentTrainDistance) {
                tunnel.trainDroveIn(train, condition);
                TrainState trainInTunnelState = new TrainDroveInTunnelState(train, tunnel);
                train.setTrainState(trainInTunnelState);
            }
        } finally {
            lock.unlock();
        }

        lock.lock();
        try {
            if (finishTunnelPoint == currentTrainDistance) {
                tunnel.trainDroveOut(train, condition);
                TrainState trainAfterTunnelState = new TrainDroveOutTunnelState(train, tunnel);
                train.setTrainState(trainAfterTunnelState);
            }
        } finally {
            lock.unlock();
        }
    }
}
