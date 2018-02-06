package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainState;
import by.epam.tunnel.entities.trainstates.TrainWaitingState;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

public class Tunnel {

    private final static Logger LOGGER = Logger.getLogger(Tunnel.class);

    private final static int EMPTY_TRAINS_INDEX = 0;
    private final static int ONE_TRAIN_INDEX = 1;
    private final static int FIRST_TRAIN_INDEX = 0;

    private LinkedList<Train> trainsInTunnel = new LinkedList<>();

    private final String name;
    private final int tunnelStartPoint;
    private final int tunnelFinishPoint;
    private final int availableTrainsInTunnel;

    private Direction currentDirection;

    public Tunnel(String name, int tunnelStartPoint, int tunnelFinishPoint, int availableTrainsInTunnel) {
        this.name = name;
        this.tunnelStartPoint = tunnelStartPoint;
        this.tunnelFinishPoint = tunnelFinishPoint;
        this.availableTrainsInTunnel = availableTrainsInTunnel;
    }

    public int getTunnelStartPoint() {
        return tunnelStartPoint;
    }

    public int getTunnelFinishPoint() {
        return tunnelFinishPoint;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void trainDroveIn(Train train, Condition available) {
        int currentTrainsInTunnel = trainsInTunnel.size();

        if (currentTrainsInTunnel == availableTrainsInTunnel) {
            try {
                available.await();
                TrainState trainWaitingState = new TrainWaitingState(train, this);
                train.setTrainState(trainWaitingState);
            } catch (InterruptedException e) {
                LOGGER.warn(e);
            }

        }

        trainsInTunnel.addLast(train);
    }

    public void trainDroveOut(Train train, Condition available) {
        trainsInTunnel.remove(train);
        available.signal();
    }

    public String getName() {
        return name;
    }
}
