package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainState;
import by.epam.tunnel.entities.trainstates.TrainWaitState;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.locks.Condition;

public class Tunnel {

    private final static Logger LOGGER = Logger.getLogger(Tunnel.class);

    private final static int EMPTY_TRAINS_INDEX = 0;
    private final static String FULL_TUNNEL = "Tunnel is full.";
    private final static String DIFFERENT_DIRECTIONS = "Directions are different.";

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
        this.currentDirection = Direction.NONE;
    }

    public int getTunnelStartPoint() {
        return tunnelStartPoint;
    }

    public int getTunnelFinishPoint() {
        return tunnelFinishPoint;
    }

    public void trainDroveIn(Train train, Condition available) {
        int currentTrainsInTunnel = trainsInTunnel.size();

        if (currentTrainsInTunnel == availableTrainsInTunnel) {
            trainWait(train, available, FULL_TUNNEL);
        }

        if (!checkTunnelDirection(train)) {
            trainWait(train, available, DIFFERENT_DIRECTIONS);
        }

        trainsInTunnel.addLast(train);
    }

    private void trainWait(Train train, Condition available, String reason) {

        TrainState trainWaitState = new TrainWaitState(train, this, reason);
        train.setTrainState(trainWaitState);

        try {
            available.await();
        } catch (InterruptedException e) {
            LOGGER.warn("Tunnel method - trainWait, interrupted exception.", e);
        }
    }

    private boolean checkTunnelDirection(Train train) {
        Direction trainDirection = train.getDirection();

        if (currentDirection == Direction.NONE) {
            currentDirection = trainDirection;

            return true;
        }

        return currentDirection == trainDirection;
    }

    public void trainDroveOut(Train train, Condition available) {
        trainsInTunnel.remove(train);
        int currentTrainsInTunnel = trainsInTunnel.size();

        if (currentTrainsInTunnel == EMPTY_TRAINS_INDEX) {
            currentDirection = Direction.NONE;
        }

        available.signal();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Tunnel tunnel = (Tunnel) object;

        return tunnelStartPoint == tunnel.tunnelStartPoint &&
                tunnelFinishPoint == tunnel.tunnelFinishPoint &&
                availableTrainsInTunnel == tunnel.availableTrainsInTunnel &&
                Objects.equals(trainsInTunnel, tunnel.trainsInTunnel) &&
                Objects.equals(name, tunnel.name) &&
                currentDirection == tunnel.currentDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainsInTunnel, name, tunnelStartPoint, tunnelFinishPoint, availableTrainsInTunnel, currentDirection);
    }

    @Override
    public String toString() {
        int currentTrainsInTunnel = trainsInTunnel.size();
        String result = String.format("Tunnel - %s. Direction to - %s. Trains in tunnel - %d",
                name, currentDirection, currentTrainsInTunnel);

        return result;

    }
}
