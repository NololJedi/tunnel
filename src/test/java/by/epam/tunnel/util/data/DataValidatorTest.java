package by.epam.tunnel.util.data;

import by.epam.tunnel.util.DataProviderSource;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class DataValidatorTest {

    private static DataValidator dataValidator;

    @BeforeClass
    public static void setDataValidator() {
        dataValidator = new DataValidator();
    }

    @DataProvider
    public static Object[][] correctParameters() {
        String[] correctParametersNorth = {"type=Train", "direction=NORTH", "distance=200"};
        String[] correctParametersWest = {"type=Train", "direction=WEST", "distance=300"};
        String[] correctParametersEast = {"type=Train", "direction=EAST", "distance=150"};
        String[] correctParametersSouth = {"type=Train", "direction=SOUTH", "distance=100"};

        return new Object[][]{
                {correctParametersEast, true},
                {correctParametersNorth, true},
                {correctParametersSouth, true},
                {correctParametersWest, true}
        };
    }

    @DataProvider
    public static Object[][] incorrectParameters() {
        String[] incorrectType = {"type=Tunnel", "direction=NORTH", "distance=200"};
        String[] incorrectDirection = {"type=Train", "direction=NONE", "distance=300"};
        String[] incorrectDistance = {"type=Train", "direction=EAST", "distance=-150"};
        String[] incorrectParametersCount = {"direction=SOUTH", "distance=100"};

        return new Object[][]{
                {incorrectDirection, false},
                {incorrectDistance, false},
                {incorrectParametersCount, false},
                {incorrectType, false}
        };
    }

    @Test
    @UseDataProvider("correctParameters")
    public void shouldValidationBeSuccessful(String[] parameters, boolean expectedResult) {
        boolean actualResult = dataValidator.checkParameters(parameters);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    @UseDataProvider("incorrectParameters")
    public void shouldValidationBeNotSuccessful(String[] parameters, boolean expectedResult) {
        boolean actualResult = dataValidator.checkParameters(parameters);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider(value = "exceptionParameters", location = DataProviderSource.class)
    public void shouldIncorrectParametersCauseException(String[] parameters) {
        dataValidator.checkParameters(parameters);
    }

}
