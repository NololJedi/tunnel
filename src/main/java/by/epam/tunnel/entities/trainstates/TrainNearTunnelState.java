package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainNearTunnelState implements TrainState {

    private final static long SPEED_NEAR_TUNNEL = 50;

    public TrainNearTunnelState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("Train - %s is approaching to tunnel - %s.", train.getId(), tunnel.getName()));
    }

    @Override
    public long move() {
        return SPEED_NEAR_TUNNEL;
    }
}
