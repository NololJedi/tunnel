package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainWaitState implements TrainState {

    private final static long WAITING_INDEX = 0;

    public TrainWaitState(Train train, Tunnel tunnel, String reason) {
        ConsoleHelper.printMessage(String.format("%s is waiting near %s Reason - %s",
                train.toString(), tunnel.toString(), reason));
    }

    @Override
    public long move() {
        return WAITING_INDEX;
    }
}
