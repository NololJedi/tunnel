package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainDroveInTunnelState;
import by.epam.tunnel.entities.trainstates.TrainDroveOutTunnelState;
import by.epam.tunnel.entities.trainstates.TrainNearTunnelState;
import by.epam.tunnel.entities.trainstates.TrainState;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainStationDispatcher {

    private final static int TUNNEL_INDEX = 10;

    private static TrainStationDispatcher instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private static Lock lock = new ReentrantLock();
    private final Condition gotthardAvailable = lock.newCondition();
    private final Condition seikanAvailable = lock.newCondition();

    private final Tunnel gotthardBasisTunnel = new Tunnel("Gotthard-Basistunnel", 15, 70, 2);
    private final Tunnel seikanTunnel = new Tunnel("Seikan Tunnel", 45, 70, 3);

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

    public Tunnel guideTrainToTunnel() {
        Random random = new Random();
        boolean randomBoolean = random.nextBoolean();

        if (randomBoolean) {
            return gotthardBasisTunnel;
        } else {
            return seikanTunnel;
        }
    }

    public void observeTunnel(Train train, int currentTrainDistance, Tunnel tunnel) {
        Condition condition = null;

        if (tunnel.equals(gotthardBasisTunnel)) {
            condition = gotthardAvailable;
        }
        if (tunnel.equals(seikanTunnel)) {
            condition = seikanAvailable;
        }

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
