package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainArrivedState;
import by.epam.tunnel.entities.trainstates.TrainDepartedState;
import by.epam.tunnel.entities.trainstates.TrainState;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Train extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Train.class);

    private static final long SPEED_INDEX = 120;

    private final Direction direction;
    private final int distance;

    private TrainStationDispatcher trainStationDispatcher;
    private TrainState trainState;

    public Train(Direction direction, int distance) {
        this.direction = direction;
        this.distance = distance;
        this.trainStationDispatcher = TrainStationDispatcher.getInstance();
    }

    @Override
    public void run() {
        trainState = new TrainDepartedState(this);

        Tunnel tunnel = trainStationDispatcher.guideTrainToTunnel();

        for (int index = 0; index < distance; index++) {
            trainStationDispatcher.observeTunnel(this, index, tunnel);
            move();
        }

        TrainState trainArrivedState = new TrainArrivedState(this);
        setTrainState(trainArrivedState);
    }

    private void move() {
        try {
            long speed = SPEED_INDEX - trainState.move();
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (InterruptedException e) {
            LOGGER.info("Train method - move, interrupted exception.", e);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setTrainState(TrainState trainState) {
        if (trainState == null) {
            throw new IllegalArgumentException("Check input train state!");
        }

        this.trainState = trainState;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Train train = (Train) object;

        return distance == train.distance &&
                direction == train.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainStationDispatcher, trainState, direction, distance);
    }

    @Override
    public String toString() {
        String result = String.format("Train: №%d. Direction - %s", getId(), getDirection());

        return result;
    }

}
