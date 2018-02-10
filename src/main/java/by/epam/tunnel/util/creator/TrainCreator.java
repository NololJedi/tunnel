package by.epam.tunnel.util.creator;

import by.epam.tunnel.entities.Direction;
import by.epam.tunnel.entities.Train;
import by.epam.tunnel.util.LineParser;

public class TrainCreator {

    public final static int TYPE_PARAMETER_INDEX = 0;
    public final static int DIRECTION_PARAMETER_INDEX = 1;
    public final static int DISTANCE_PARAMETER_INDEX = 2;

    private final static int VALUE_PARAMETER_INDEX = 1;

    public Train createTrain(String[] parameters) {
        if (parameters == null || parameters.length == 0) {
            throw new IllegalArgumentException("Null parameters.");
        }

        Direction direction;
        int distance;

        String directionParameter = parameters[DIRECTION_PARAMETER_INDEX];
        String distanceParameter = parameters[DISTANCE_PARAMETER_INDEX];

        String[] parsedDirection = LineParser.parseLine(directionParameter, LineParser.PARAMETER_PARSER_INDICATOR);
        String directionValue = parsedDirection[VALUE_PARAMETER_INDEX];
        direction = Direction.valueOf(directionValue);

        String[] parsedDistance = LineParser.parseLine(distanceParameter, LineParser.PARAMETER_PARSER_INDICATOR);
        String distanceValue = parsedDistance[VALUE_PARAMETER_INDEX];
        distance = Integer.parseInt(distanceValue);

        return new Train(direction, distance);
    }

}
