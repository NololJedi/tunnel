package by.epam.tunnel.util;

public class LineParser {

    public static final String DATA_PARSER_INDICATOR = " ";
    public static final String PARAMETER_PARSER_INDICATOR = "=";

    public static String[] parseLine(String line, String parserIndicator) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Empty line to parse.");
        }
        if (parserIndicator == null || parserIndicator.isEmpty()) {
            throw new IllegalArgumentException("Empty parser indicator.");
        }

        String[] parsedLine = line.split(parserIndicator);

        if (parsedLine.length == 1){
            throw new IllegalArgumentException("Line was parsed incorrect.");
        }

        return parsedLine;
    }

}
