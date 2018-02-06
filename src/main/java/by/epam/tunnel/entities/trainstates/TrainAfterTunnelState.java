package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainAfterTunnelState implements TrainState {

    private final static long MAX_SPEED = 100;

    public TrainAfterTunnelState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("Train - %s drove out tunnel - %s.", train.getName(), tunnel.getName()));
    }

    @Override
    public long move() {
        return MAX_SPEED;
    }
}
