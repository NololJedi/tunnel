package by.epam.tunnel.run;

import by.epam.tunnel.entities.Direction;
import by.epam.tunnel.entities.Train;

public class TunnelRunner {

    public static void main(String[] args) {

        Train train2 = new Train(Direction.NORTH, 300);
        Train train1 = new Train(Direction.WEST, 200);
        Train train3 = new Train(Direction.EAST, 250);
        Train train4 = new Train(Direction.SOUTH, 300);
        Train train5 = new Train(Direction.WEST, 200);
        Train train6 = new Train(Direction.SOUTH, 250);
        Train train7 = new Train(Direction.NORTH, 300);
        Train train8 = new Train(Direction.WEST, 200);
        Train train9 = new Train(Direction.EAST, 250);
        Train train10 = new Train(Direction.NORTH, 300);
        Train train11 = new Train(Direction.WEST, 200);
        Train train12 = new Train(Direction.EAST, 250);

        train2.start();
        train1.start();
        train3.start();
        train4.start();
        train5.start();
        train6.start();
        train7.start();
        train8.start();
        train9.start();
        train10.start();
        train11.start();
        train12.start();

    }
}
