package by.epam.tunnel.util.creator;

import by.epam.tunnel.entities.Direction;
import by.epam.tunnel.entities.Train;
import by.epam.tunnel.util.DataProviderSource;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class TrainCreatorTest {

    private final static Train EXPECTED_TRAIN = new Train(Direction.SOUTH, 200);

    private static TrainCreator trainCreator;

    @BeforeClass
    public static void setTrainCreator() {
        trainCreator = new TrainCreator();
    }

    @Test
    public void shouldCreationBeSuccessful() {
        String[] parameters = {"type=Train", "direction=SOUTH", "distance=200"};
        Train actualTrain = trainCreator.createTrain(parameters);

        Assert.assertEquals(EXPECTED_TRAIN, actualTrain);
    }

    @Test
    public void shouldTrainsBeDifferent() {
        String[] parameters = {"type=Train", "direction=WEST", "distance=200"};
        Train actualTrain = trainCreator.createTrain(parameters);

        Assert.assertNotEquals(EXPECTED_TRAIN, actualTrain);
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider(value = "exceptionParameters", location = DataProviderSource.class)
    public void shouldCreationCauseIllegalArgumentException(String[] parameters){
        trainCreator.createTrain(parameters);
    }

}
