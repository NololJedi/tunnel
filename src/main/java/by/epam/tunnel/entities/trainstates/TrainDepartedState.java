package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainDepartedState implements TrainState {

    private final static long START_SPEED = 70;

    public TrainDepartedState(Train train) {
        ConsoleHelper.printMessage(String.format("%s is departed from depot.", train.toString()));
    }

    @Override
    public long move() {
        return START_SPEED;
    }

}
