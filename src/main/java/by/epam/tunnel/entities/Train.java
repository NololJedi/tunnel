package by.epam.tunnel.entities;

import by.epam.tunnel.entities.trainstates.TrainArrivedState;
import by.epam.tunnel.entities.trainstates.TrainDepartedState;
import by.epam.tunnel.entities.trainstates.TrainState;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Train extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Train.class);

    private TrainStationDispatcher trainStationDispatcher;
    private TrainState trainState;
    private final Direction direction;
    private final int distance;

    public Train(Direction direction, int distance) {
        this.direction = direction;
        this.distance = distance;
        this.trainStationDispatcher = TrainStationDispatcher.getInstance();
        trainState = new TrainDepartedState(this);
    }

    @Override
    public void run() {

        for (int index = 0; index < distance; index++) {
            trainStationDispatcher.observeTunnels(this, index);
            move();
        }

        TrainState trainArrivedState = new TrainArrivedState(this);
        setTrainState(trainArrivedState);
    }

    private void move() {
        try {
            TimeUnit.MILLISECONDS.sleep(trainState.move());
        } catch (InterruptedException e) {
            LOGGER.info("Train move method interrupted exception.", e);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setTrainState(TrainState trainState) {
        this.trainState = trainState;
    }
}
