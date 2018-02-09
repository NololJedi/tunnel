package by.epam.tunnel.run;

import by.epam.tunnel.entities.Train;
import org.apache.log4j.Logger;

import java.util.List;


public class TunnelRunner {

    private final static Logger LOGGER = Logger.getLogger(TunnelRunner.class);

    public static void main(String[] args) {
        LOGGER.info("Start program.");

        TrainDirector trainDirector = new TrainDirector();
        List<Train> trains = trainDirector.getTrainsFromFile(TrainDirector.FILE_NAME);

//        if (trains != null || trains.size() != 0){
//            for (Train train : trains) {
//                //train.start();
//            }
//        } else {
//            LOGGER.info("No trains were detected.");
//        }

    }
}
