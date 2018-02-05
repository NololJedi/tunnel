package by.epam.tunnel.entities;
import org.apache.log4j.Logger;


import java.util.concurrent.TimeUnit;

import static by.epam.tunnel.util.ConsoleHelper.*;

public class Train extends Thread{

    private static final Logger LOGGER = Logger.getLogger(Train.class);
    private TrainStationDispatcher trainStationDispatcher;
    private final Direction direction;
    private final int distance;
    private Tunnel tunnel;
    private long speed;

    public Train(Tunnel tunnel, Direction direction, int distance, long speed, TrainStationDispatcher trainStationDispatcher){
        this.tunnel = tunnel;
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;
        this.trainStationDispatcher = trainStationDispatcher;
    }

    @Override
    public void run() {
        printMessage(String.format("Train - %s: to - %s is departed.",this.getName(), direction.toString()));
        int startTunnel = tunnel.getTunnelStartPoint();
        int endTunnel = tunnel.getTunnelFinishPoint();

        for (int index = 0; index < distance; index++) {

            try {
                TimeUnit.MILLISECONDS.sleep(speed);
            } catch (InterruptedException e) {
                LOGGER.info("Interrupted!", e);
            }

            if (index == startTunnel){
                tunnel.trainDroveIn(this);
            }
            if (index == endTunnel){
                tunnel.trainDroveOut(this);
            }

        }
        printMessage(String.format("Train - %s: to - %s is arrived.",this.getName(), direction.toString()));
    }

    public void setSpeed(long speed) {
        if (speed < 0){
            throw new IllegalArgumentException("Incorrect speed.");
        }

        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }
}
