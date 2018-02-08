package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainDroveOutTunnelState implements TrainState {

    private final static long MAX_SPEED = 100;

    public TrainDroveOutTunnelState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("%s drove out tunnel - %s.", train.toString(), tunnel.toString()));
    }

    @Override
    public long move() {
        return MAX_SPEED;
    }
}
