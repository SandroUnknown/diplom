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
    public void createProjects(AccountData testData, int projectCount) {

        // Получаем все проекты из нашего шаблона
        List<ProjectResponseModel> projects = PROJECT_TEMPLATES.getProjects();

        // Проверяем, что мы создаём корректное количество проектов (не больше, чем есть в шаблоне)
        if (projectCount > projects.size()) {
            // Выбросить исключение, что "Исходный шаблон содержит меньшее количество проектов, чем вы пытаетесь создать."
        } 
        
        // Создаем проекты
        for(int i = 0; i < projects.size(); i++) {
            ProjectResponseModel project = projects.get(i);
            ProjectRequestModel request = ProjectRequestModel.builder()
                .name(project.getName())
                .color(project.getColor())
                .viewStyle(project.getViewStyle())
                .build();

            project = api.createNewProject(request);
            testData.getProjects().add(project);
        }
    }


    // === Создание РАЗДЕЛА в проекте ===
    public void createSections(AccountData testData, List<int> sectionCount) {

        // Получаем количество проектов, которые мы уже создали.
        int projectCount = accountData.getProjects().size();

        // Проверяем, что мы уже создали столько же проектов, сколько сейчас передали "секций"
        if (projectCount != sectionCount.size()) {
            // Выбросить исключение, что "Ожидаемое количество созданных проектов не соответствует фактическому количеству созданных проектов."
        }

        // Считаем сколько всего хотим создать разделов.
        int totalSectionCount = 0;
        for(int k : sectionCount) {
            totalSectionCount += k;
        }
        
        // Получаем ВСЕ разделы из нашего шаблона
        List<SectionResponseModel> sections = PROJECT_TEMPLATES.getSections();

        // Проверяем, что мы в нашем шаблоне есть нужное количество разделов
        if (totalSectionCount > sections.size()) {
            // Выбросить исключение, что "Количество разделов в шаблоне меньше, чем вы пытаетесь создать."
        }

        // Создаем разделы
        int k = 0;
        for(int i = 0; i < sectionCount.size(); i++) {
            String projectId = testData.getProjects().get(i).getId();
            for(int j = 0; j < sectionCount.get(i); j++) {
                SectionResponseModel section = sections.get(k);
                SectionRequestModel request = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name(section.getName())    // TODO : набор данных для РАЗДЕЛА
                    //.color(section.getColor())
                    //.viewStyle(section.getViewStyle())
                    .build();
                section = api.createNewSection(request);
                testData.getSections().add(section);
                k++;     
            }
        }
    }
    
    // === (перегрузка) Создание РАЗДЕЛА в проекте ===
    public void createSections(AccountData testData, int sectionCount) {  

        // Получаем количество проектов, которые мы уже создали.
        int projectCount = accountData.getProjects().size();

        // Создаем массив с количеством секций для каждого проекта
        List<int> arraySectionCount = new ArrayList<>();
        for(int i = 0; i < projectCount; i++) {
            arraySectionCount.add(sectionCount);
        }

        // Вызываем основной метод.
        createSections(testData, arraySectionCount);
    }
    


    
}
