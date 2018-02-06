package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainInTunnelState implements TrainState {
    private final static long SPEED_IN_TUNNEL = 40;


    public TrainInTunnelState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("Train - %s is moving through tunnel - %s.", train.getName(), tunnel.getName()));
    }

    @Override
    public long move() {
        return SPEED_IN_TUNNEL;
    }
}
