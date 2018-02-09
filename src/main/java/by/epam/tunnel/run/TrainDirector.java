package by.epam.tunnel.run;

import by.epam.tunnel.entities.Train;
import by.epam.tunnel.exceptions.DataLoadException;
import by.epam.tunnel.exceptions.IncorrectParameterException;
import by.epam.tunnel.util.LineParser;
import by.epam.tunnel.util.creator.TrainCreator;
import by.epam.tunnel.util.data.DataLoader;
import by.epam.tunnel.util.data.DataValidator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainDirector {

    private final static Logger LOGGER = Logger.getLogger(TrainDirector.class);

    public final static String FILE_NAME = "./src/main/resources/data.txt";

    public List<Train> getTrainsFromFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Check file name.");
        }

        LOGGER.info(String.format("Try to load data from file - %s.", fileName));
        List<String> data = null;

        try {
            DataLoader dataLoader = new DataLoader();
            data = dataLoader.loadDataFromFile(fileName);
        } catch (DataLoadException e) {
            LOGGER.warn(String.format("Can't load data from file - %s.", fileName));
        }

        if (data == null) {
            LOGGER.info("Data wasn't loaded.");

            return null;
        } else {
            LOGGER.info("Data was loaded successful.");
        }

        LOGGER.info("Try to create trains from data.");

        List<Train> trains = new ArrayList<>();
        DataValidator dataValidator = new DataValidator();
        TrainCreator trainCreator = new TrainCreator();

        for (int listIndex = 0; listIndex < data.size(); listIndex++) {
            String line = data.get(listIndex);

            try {
                String[] parameters = LineParser.parseLine(line, LineParser.DATA_PARSER_INDICATOR);
                boolean checkParameters = dataValidator.checkParameters(parameters);

                if (checkParameters) {
                    Train train = trainCreator.createTrain(parameters);
                    LOGGER.info(String.format("Train was created successfully from parameters - %s.", Arrays.toString(parameters)));

                    trains.add(train);
                } else {
                    LOGGER.info(String.format("Incorrect parameters detected - %s. " +
                            "Train can't be created", Arrays.toString(parameters)));
                }
            } catch (IllegalArgumentException | IncorrectParameterException e) {
                LOGGER.warn("Train creation cause exception." + e.getLocalizedMessage());
            }

        }

        return trains;
    }
}
