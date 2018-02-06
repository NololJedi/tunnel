package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainArrivedState implements TrainState {

    private final static long BRAKE = 0;

    public TrainArrivedState(Train train) {
        ConsoleHelper.printMessage(String.format("Train - %s is arrived to station.", train.getName()));
    }

    @Override
    public long move() {
        return BRAKE;
    }
}
