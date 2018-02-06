package by.epam.tunnel.run;

import by.epam.tunnel.entities.Direction;
import by.epam.tunnel.entities.Train;

public class TunnelRunner {

    public static void main(String[] args) {

        Train train = new Train(Direction.NORTH, 300);
        Train train1 = new Train(Direction.NORTH, 200);
        Train train3 = new Train(Direction.NORTH, 250);
        Train train4 = new Train(Direction.NORTH, 150);
        Train train5 = new Train(Direction.NORTH, 170);
        Train train6 = new Train(Direction.NORTH, 250);
        train.start();
        train1.start();
        train3.start();
        train4.start();
        train5.start();
        train6.start();

    }
}
