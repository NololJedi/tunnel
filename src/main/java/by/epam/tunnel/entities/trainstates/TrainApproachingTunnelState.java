package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainApproachingTunnelState implements TrainState {

    private final static long SPEED_NEAR_TUNNEL = 50;

    public TrainApproachingTunnelState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("%s is approaching to %s.", train.toString(), tunnel.toString()));
    }

    @Override
    public long move() {
        return SPEED_NEAR_TUNNEL;
    }
}
