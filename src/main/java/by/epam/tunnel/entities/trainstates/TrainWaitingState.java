package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainWaitingState implements TrainState {

    private final static long WAITING_INDEX = 0;

    public TrainWaitingState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("Train - %s is waiting near tunnel - %s.", train.getName(), tunnel.getName()));
    }

    @Override
    public long move() {
        return WAITING_INDEX;
    }
}
