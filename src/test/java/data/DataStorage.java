package data;

import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.qameta.allure.internal.shadowed.jackson.databind.PropertyNamingStrategy;
import models.data.TestDataModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class DataStorage {

    private final List<TestDataModel> templates;

    public DataStorage(String fileName) {
        this.templates = loadTemplatesFromFile(fileName);
    }

    private static List<TestDataModel> loadTemplatesFromFile(String fileName) {

        ClassLoader cl = DataStorage.class.getClassLoader();
        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        try (Reader reader = new InputStreamReader(cl.getResourceAsStream(fileName))) {
            return Arrays.asList(om.readValue(reader, TestDataModel[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TestDataModel> getTemplates() {
        return templates;
    }
}