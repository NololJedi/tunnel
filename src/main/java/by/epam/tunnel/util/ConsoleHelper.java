package by.epam.tunnel.util;

import org.apache.log4j.Logger;

public class ConsoleHelper {

    private final static Logger LOGGER = Logger.getLogger(ConsoleHelper.class);

    public static void printMessage(String message) {
        System.out.println(message);
        LOGGER.info(message);
    }

}
