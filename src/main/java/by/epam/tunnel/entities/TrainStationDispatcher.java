package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainApproachingTunnelState;
import by.epam.tunnel.entities.trainstates.TrainDroveInTunnelState;
import by.epam.tunnel.entities.trainstates.TrainDroveOutTunnelState;
import by.epam.tunnel.entities.trainstates.TrainState;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainStationDispatcher {

    private final static int TUNNEL_INDEX = 10;

    private static TrainStationDispatcher instance = null;
    private static AtomicBoolean isInstanceAvailable;

    private final static Lock lock;
    private final static Condition gotthardAvailable;
    private final static Condition seikanAvailable;

    private final static Tunnel gotthardBasisTunnel;
    private final static Tunnel seikanTunnel;

    static {
        isInstanceAvailable = new AtomicBoolean(true);
        lock = new ReentrantLock();
        gotthardAvailable = lock.newCondition();
        seikanAvailable = lock.newCondition();

        gotthardBasisTunnel = new Tunnel("Gotthard-Basistunnel", 15, 70, 2);
        seikanTunnel = new Tunnel("Seikan Tunnel", 45, 70, 3);
    }

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
        if (train == null) {
            throw new IllegalArgumentException("Train is null!");
        }
        if (currentTrainDistance < 0) {
            throw new IllegalArgumentException("Incorrect current train distance, bellow zero!");
        }
        if (tunnel == null) {
            throw new IllegalArgumentException("Tunnel is null!");
        }

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
            TrainState trainApproachingTunnelState = new TrainApproachingTunnelState(train, tunnel);
            train.setTrainState(trainApproachingTunnelState);
        }

        lock.lock();
        try {
            if (startTunnelPoint == currentTrainDistance) {
                tunnel.trainDroveIn(train, condition);

                TrainState trainInTunnelState = new TrainDroveInTunnelState(train, tunnel);
                train.setTrainState(trainInTunnelState);
            }

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
