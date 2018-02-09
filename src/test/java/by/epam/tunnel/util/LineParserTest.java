package by.epam.tunnel.util;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class LineParserTest {

    @DataProvider
    public static Object[][] incorrectInputParameter() {
        String nullParameter = null;
        String emptyParameter = "";
        String inCorrectParameter = "type=Train!direction=NORTH";

        return new Object[][]{
                {emptyParameter},
                {nullParameter},
                {inCorrectParameter},

        };
    }

    @Test
    public void shouldDataParsingBeSuccessful() {
        String line = "type=Train direction=NORTH distance=200";
        String[] expectedParsedData = {"type=Train", "direction=NORTH", "distance=200"};
        String[] actualParsedData = LineParser.parseLine(line, LineParser.DATA_PARSER_INDICATOR);

        Assert.assertArrayEquals(expectedParsedData, actualParsedData);
    }

    @Test
    public void shouldDataParsingBeNotSuccessful() {
        String line = "type=Train direction=NORTH distance=200";
        String[] expectedParsedData = {"type=Train direction=NORTH", "distance=200"};
        String[] actualParsedData = LineParser.parseLine(line, LineParser.DATA_PARSER_INDICATOR);

        Assert.assertNotEquals(expectedParsedData, actualParsedData);
    }

    @Test
    public void shouldValueParsingBeSuccessful() {
        String value = "distance=200";
        String[] expectedParsedValue = {"distance", "200"};
        String[] actualParsedValue = LineParser.parseLine(value, LineParser.PARAMETER_PARSER_INDICATOR);

        Assert.assertArrayEquals(expectedParsedValue, actualParsedValue);
    }

    @Test
    public void shouldValueParsingBeNotSuccessful() {
        String value = "distance=200";
        String[] expectedParsedValue = {"distance=", "200"};
        String[] actualParsedValue = LineParser.parseLine(value, LineParser.PARAMETER_PARSER_INDICATOR);

        Assert.assertNotEquals(expectedParsedValue, actualParsedValue);
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("incorrectInputParameter")
    public void shouldIncorrectLineCauseException(String line) {
        LineParser.parseLine(line, LineParser.DATA_PARSER_INDICATOR);
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("incorrectInputParameter")
    public void shouldIncorrectParserIndicatorCauseException(String parserIndicator) {
        String line = "type=Train direction=NORTH distance=200";

        LineParser.parseLine(line, parserIndicator);
    }
}
