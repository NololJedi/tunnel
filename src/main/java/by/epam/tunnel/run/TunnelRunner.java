package by.epam.tunnel.run;

import by.epam.tunnel.entities.Direction;
import by.epam.tunnel.entities.Train;
import by.epam.tunnel.entities.TrainStationDispatcher;
import by.epam.tunnel.entities.Tunnel;


public class TunnelRunner {

    public static void main(String[] args) {
        Tunnel tunnel = new Tunnel(50,100, "Everest");
        TrainStationDispatcher trainStationDispatcher = TrainStationDispatcher.getInstance();
        Train train = new Train(tunnel,Direction.NORTH,150,100,trainStationDispatcher);
        Train train1 = new Train(tunnel,Direction.NORTH,200,200,trainStationDispatcher);
        Train train2 = new Train(tunnel,Direction.NORTH,150,150,trainStationDispatcher);
        Train train3 = new Train(tunnel,Direction.NORTH,150,150,trainStationDispatcher);
        Train train4 = new Train(tunnel,Direction.NORTH,150,150,trainStationDispatcher);

        train.start();
        train1.start();
        train2.start();
        train3.start();
        train4.start();
    }

}
