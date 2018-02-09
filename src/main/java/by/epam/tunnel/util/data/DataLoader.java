package by.epam.tunnel.util.data;

import by.epam.tunnel.exceptions.DataLoadException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public List<String> loadDataFromFile(String fileName) throws DataLoadException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Check file name.");
        }

        List<String> data = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            while (fileReader.ready()) {
                String dataFromFile = fileReader.readLine();
                dataFromFile = dataFromFile.replaceAll("\r\n", "");
                dataFromFile = dataFromFile.trim();
                data.add(dataFromFile);
            }

            return data;

        } catch (IOException exception) {
            throw new DataLoadException(String.format("Can't load data from file - %s.", fileName), exception);
        }
    }

}
