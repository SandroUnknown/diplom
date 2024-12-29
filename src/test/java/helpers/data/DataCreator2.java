package helpers.data;

import api.*;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.qameta.allure.internal.shadowed.jackson.databind.PropertyNamingStrategy;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;
import models.data.TestData;
import models.data.TestDataConfig;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class DataCreator {

    // TODO : передавать имя файла в конструкторе?
    public static final List<TestData> PROJECT_TEMPLATES = getProjectTemplatesFromFile("data/ProjectTemplates.json");

    private static List<TestData> getProjectTemplatesFromFile(String fileName) {

        ClassLoader cl = DataCreator.class.getClassLoader();
        ObjectMapper om = new ObjectMapper();

        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        try (Reader reader = new InputStreamReader(cl.getResourceAsStream(fileName))) {
            return Arrays.asList(om.readValue(reader, TestData[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TestData create(int templateId, TestDataConfig whatIsCreate) {

        TestData templateData = PROJECT_TEMPLATES.get(templateId);
        TestData testData = new TestData();

        createLabels(testData, templateData, whatIsCreate);
        createProjects(testData, templateData, whatIsCreate);
        createCommentsInProjects(testData, templateData, whatIsCreate);
        createTasksInProjects(testData, templateData, whatIsCreate);
        createCommentsInTasksInProjects(testData, templateData, whatIsCreate);
        createSections(testData, templateData, whatIsCreate);
        createTasksInSections(testData, templateData, whatIsCreate);
        createCommentsInTasksInSections(testData, templateData, whatIsCreate);

        return testData;
    }

    // === Создание ПРОЕКТА в корне ===
    public void createProjects(myData, projectCount) {
        
    }


    // === Создание РАЗДЕЛА в проекте ===
    public void createSections(myData, projectCount) {
        
    }
    


    
}
