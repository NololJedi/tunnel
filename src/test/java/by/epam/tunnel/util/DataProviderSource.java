package by.epam.tunnel.util;

import com.tngtech.java.junit.dataprovider.DataProvider;

public class DataProviderSource {

    @DataProvider
    public static Object[][] exceptionParameters() {
        String[] nullParameter = null;
        String[] emptyParameters = {};

        return new Object[][]{
                {nullParameter},
                {emptyParameters}
        };
    }

}
