package by.epam.tunnel.entities.trainstates;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.Tunnel;
import by.epam.tunnel.util.ConsoleHelper;

public class TrainDroveInTunnelState implements TrainState {
    private final static long SPEED_IN_TUNNEL = 40;


    public TrainDroveInTunnelState(Train train, Tunnel tunnel) {
        ConsoleHelper.printMessage(String.format("Train - %s drove in tunnel - %s.", train.getId(), tunnel.getName()));
    }

    @Override
    public long move() {
        return SPEED_IN_TUNNEL;
    }
}
