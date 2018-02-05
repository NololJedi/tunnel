package by.epam.tunnel.entities;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static by.epam.tunnel.util.ConsoleHelper.printMessage;

public class Tunnel {

    private final static Logger LOGGER = Logger.getLogger(Tunnel.class);
    private final static int AVAILABLE_COUNT_OF_TRAINS = 2;

    private final static long MAX_SPEED_AVAILABLE_IN_TUNNEL = 50;
    private final static int EMPTY_TRAINS_INDEX = 0;
    private final static int ONE_TRAIN_INDEX = 1;
    private final static int FIRST_TRAIN_INDEX = 0;

    private ReentrantLock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();
    private LinkedList<Train> trains = new LinkedList<>();

    private final int tunnelStartPoint;
    private final int tunnelFinishPoint;
    private final String name;
    private Direction currentDirection;

    public Tunnel(int tunnelStartPoint, int tunnelFinishPoint, String name) {
        this.tunnelStartPoint = tunnelStartPoint;
        this.tunnelFinishPoint = tunnelFinishPoint;
        this.name = name;
    }

    public int getTunnelStartPoint() {
        return tunnelStartPoint;
    }

    public int getTunnelFinishPoint() {
        return tunnelFinishPoint;
    }


    public void trainDroveIn(Train train) {
        lock.lock();
        int currentTrainsInTunnel = trains.size();

        try {
            setCurrentDirection();
            if (currentTrainsInTunnel == AVAILABLE_COUNT_OF_TRAINS){
                printMessage(String.format("Train - %s is waiting.", train.getName()));
                available.await();

            }
            trains.addLast(train);
            train.setSpeed(MAX_SPEED_AVAILABLE_IN_TUNNEL);
            printMessage(String.format("Train - %s drove in tunnel: %s.", train.getName(), name));
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!",e);
        } finally {
            lock.unlock();
        }
    }

    public void trainDroveOut(Train train) {
        lock.lock();
        long trainSpeed = train.getSpeed();

        try {
            trains.removeFirst();
            setCurrentDirection();
            available.signal();
            train.setSpeed(trainSpeed);
            printMessage(String.format("Train - %s drove out tunnel: %s.", train.getName(), name));
        } finally {
            lock.unlock();
        }
    }

    private void setCurrentDirection() {
        lock.lock();
        int currentTrainsCount = trains.size();

        try {

            if (currentTrainsCount == EMPTY_TRAINS_INDEX) {
                currentDirection = Direction.NONE;
            }
            if (currentTrainsCount == ONE_TRAIN_INDEX) {
                Train currentTrain = trains.get(FIRST_TRAIN_INDEX);
                currentDirection = currentTrain.getDirection();
            }

        } finally {
            lock.unlock();
        }


    }

}
