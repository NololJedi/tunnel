package by.epam.tunnel.util.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.tunnel.util.creator.TrainCreator.DIRECTION_PARAMETER_INDEX;
import static by.epam.tunnel.util.creator.TrainCreator.DISTANCE_PARAMETER_INDEX;
import static by.epam.tunnel.util.creator.TrainCreator.TYPE_PARAMETER_INDEX;

public class DataValidator {
    private final static int PARAMETERS_COUNT = 3;

    private final static String TYPE_PATTERN_OF_VALIDATION = "^type=Train$";
    private final static String DIRECTION_PATTERN_OF_VALIDATION = "^direction=(NORTH|WEST|SOUTH|EAST)$";
    private final static String DISTANCE_PATTERN_OF_VALIDATION = "^distance=\\d+$";

    public boolean checkParameters(String[] parameters) {
        if (parameters == null || parameters.length == 0) {
            throw new IllegalArgumentException("Null parameters.");
        }

        int currentCount = parameters.length;
        boolean checkParametersCount = checkParametersCount(currentCount);

        if (!checkParametersCount) {
            return false;
        }

        String typeParameter = parameters[TYPE_PARAMETER_INDEX];
        boolean checkType = checkParameter(typeParameter, TYPE_PATTERN_OF_VALIDATION);

        String directionParameter = parameters[DIRECTION_PARAMETER_INDEX];
        boolean checkDirection = checkParameter(directionParameter, DIRECTION_PATTERN_OF_VALIDATION);

        String distanceParameter = parameters[DISTANCE_PARAMETER_INDEX];
        boolean checkDistance = checkParameter(distanceParameter, DISTANCE_PATTERN_OF_VALIDATION);

        return checkDirection && checkDistance && checkType;
    }

    private boolean checkParametersCount(int currentCount) {
        return currentCount == PARAMETERS_COUNT;
    }

    private boolean checkParameter(String parameter, String patternOfValidation) {
        Pattern pattern = Pattern.compile(patternOfValidation);
        Matcher matcher = pattern.matcher(parameter);

        return matcher.matches();
    }

}
