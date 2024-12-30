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
    public static final List<TestData> TEMPLATES = getProjectTemplatesFromFile("data/ProjectTemplates.json");

    // Чтение из файла в переменную-шаблон
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
    // ==========================================================================================================================================================================
    

    // ==========================================================================================================================================================================
    // === Создание ПРОЕКТА в корне ===
    public void createProjects(AccountData testData, int projectCount) {    // локальная переменная с моими данными, количество проектов (вероятно ещё добавить переменную шаблона?)
        
        // Получаем все проекты из нашего шаблона
        List<ProjectResponseModel> projectTemplates = TEMPLATES.getProjects();

        // Проверяем, что мы создаём корректное количество проектов (не больше, чем есть в шаблоне)
        if (projectCount > projectTemplates.size()) {
            // Выбросить исключение, что "Исходный шаблон содержит меньшее количество проектов, чем вы пытаетесь создать."
        } 
        
        // Создаем проекты
        for(int i = 0; i < projectCount; i++) {
            /*ProjectResponseModel project = projectTemplates.get(i);
            ProjectRequestModel request = ProjectRequestModel.builder()
                .name(project.getName())
                .color(project.getColor())
                .viewStyle(project.getViewStyle())
                .build();*/
            ProjectRequestModel request = projectTemplates.get(i);

            //project = api.createNewProject(request);
            ProjectResponseModel project = api.createNewProject(request);
            testData.getProjects().add(project);
        }
    }
    // ==========================================================================================================================================================================

    
    // ==========================================================================================================================================================================
    // === Создание РАЗДЕЛА в проекте ===
    public void createSections(AccountData testData, List<int> sectionCount) {
        
        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Проверяем, что мы уже создали столько же проектов, сколько элементов разделов передали сейчас
        if (createdProjectCount != sectionCount.size()) {
            // Выбросить исключение, что "Ожидаемое количество созданных проектов не соответствует фактическому количеству созданных проектов."
        }

        // Считаем сколько всего хотим создать разделов.
        int totalSectionCount = 0;
        for(int count : sectionCount) {
            totalSectionCount += count;
        }
        
        // Получаем ВСЕ разделы из нашего шаблона
        List<SectionResponseModel> sectionTemplates = TEMPLATES.getSections();

        // Проверяем, что в нашем шаблоне есть нужное количество разделов
        if (totalSectionCount > sectionTemplates.size()) {
            // Выбросить исключение, что "Количество разделов в шаблоне меньше, чем вы пытаетесь создать."
        }

        // Создаем разделы
        int sectionTemplateNumber = 0;
        for(int i = 0; i < createdProjectCount; i++) {
            String projectId = testData.getProjects().get(i).getId();
            for(int j = 0; j < sectionCount.get(i); j++) {
                /*SectionResponseModel section = sectionTemplates.get(sectionTemplateNumber);
                SectionRequestModel request = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name(section.getName())    // TODO : набор данных для РАЗДЕЛА
                    .build();*/
                SectionRequestModel request = sectionTemplates.get(sectionTemplateNumber);
                request.setProjectId(projectId);
                
                //section = api.createNewSection(request);
                SectionResponseModel section = api.createNewSection(request);
                testData.getSections().add(section);
                sectionTemplateNumber++;     
            }
        }
    }
    
    // === (перегрузка) Создание РАЗДЕЛА в проекте ===
    public void createSections(AccountData testData, int sectionCount) {  

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = accountData.getProjects().size();

        // Создаем массив с количеством секций для каждого проекта
        List<int> arraySectionCount = new ArrayList<>();
        for(int i = 0; i < createdProjectCount; i++) {
            arraySectionCount.add(sectionCount);
        }

        // Вызываем основной метод.
        createSections(testData, arraySectionCount);
    }
    // ==========================================================================================================================================================================


    // ==========================================================================================================================================================================
    // === Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, List<int> taskCount, boolean addLabels) {

        // Получаем количество разделов, которые мы уже создали.
        int createdSectionCount = testData.getSections().size();

        // Проверяем, что мы уже создали столько же разделов, сколько элементов задач передали сейчас
        if (createdSectionCount != taskCount.size()) {
            // Выбросить исключение, что "Ожидаемое количество созданных разелов не соответствует фактическому количеству созданных разделов."
        }

        // Считаем сколько всего хотим создать задач.
        int totalTaskCount = 0;
        for(int count : taskCount) {
            totalTaskCount += count;
        }
        
        // Получаем ВСЕ задачи из нашего шаблона
        List<TaskResponseModel> taskTemplates = TEMPLATES.getTasksInSections();

        // Проверяем, что в нашем шаблоне есть нужное количество задач
        if (totalTaskCount > taskTemplates.size()) {
            // Выбросить исключение, что "Количество задач в шаблоне меньше, чем вы пытаетесь создать."
        }

        // Создаем задачи.
        int taskTemplateNumber = 0;
        for(int i = 0; i < createdSectionCount; i++) {
            String sectionId = testData.getSections().get(i).getId();
            for(int j = 0; j < taskCount.get(i); j++) {
                /*TaskResponseModel task = taskTemplates.get(taskTemplateNumber);
                TaskRequestModel request = TaskRequestModel.builder()
                    .sectionId(sectionId)
                    .content(task.getContent())    // TODO : набор данных для ЗАДАЧИ
                    .color(task.getColor())
                    //.labels(section.getViewStyle())    // TODO : LABELS
                    .build();*/
                TaskRequestModel request = taskTemplates.get(taskTemplateNumber);
                request.setSectionId(sectionId);
                if (!addLabels) { // если метки не нужны (false)
                    request.setLabels(null); // TODO : LABELS  
                }
                
                //task = api.createNewTask(request);
                TaskResponseModel task = api.createNewTask(request);
                testData.getTasks().add(task);
                taskTemplateNumber++;     
            }
        }
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, List<int> taskCount) {
        createTasksInSections(testData, taskCount, false) {
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, int taskCount, boolean addLabels) {  

        // Получаем количество разделов, которые мы уже создали.
        int createdSectionCount = testData.getSections().size();

        // Создаем массив с количеством задач для каждого раздела
        List<int> arrayTaskCount = new ArrayList<>();
        for(int i = 0; i < createdSectionCount; i++) {
            arrayTaskCount.add(taskCount);
        }

        // Вызываем основной метод.
        createTasksInSections(testData, arrayTaskCount, addLabels);
    }

    // === (перегрузка) Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, int taskCount) {
        createTasksInSections(testData, taskCount, false)
    }

    // ==========================================================================================================================================================================

        
    // ==========================================================================================================================================================================
    // === Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, List<int> taskCount, boolean addLabels) {

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Проверяем, что мы уже создали столько же проектов, сколько элементов задач передали сейчас
        if (createdProjectCount != taskCount.size()) {
            // Выбросить исключение, что "Ожидаемое количество созданных проектов не соответствует фактическому количеству созданных проектов."
        }
        
        // Считаем сколько всего хотим создать задач.
        int totalTaskCount = 0;
        for(int count : taskCount) {
            totalTaskCount += count;
        }   
        
        // Получаем ВСЕ задачи из нашего шаблона
        List<TaskResponseModel> taskTemplates = TEMPLATES.getTasksInProjects();

        // Проверяем, что в нашем шаблоне есть нужное количество задач
        if (totalTaskCount > taskTemplates.size()) {
            // Выбросить исключение, что "Количество задач в шаблоне меньше, чем вы пытаетесь создать."
        }

        // Создаем задачи.
        int taskTemplateNumber = 0;
        for(int i = 0; i < createdProjectCount; i++) {
            String projectId = testData.getProjects().get(i).getId();
            for(int j = 0; j < taskCount.get(i); j++) {
                TaskRequestModel request = taskTemplates.get(taskTemplateNumber);
                request.setProjectId(projectId);
                if (!addLabels) { // если метки не нужны (false)
                    request.setLabels(null); // TODO : LABELS  
                }
                
                TaskResponseModel task = api.createNewTask(request);
                testData.getTasks().add(task);
                taskTemplateNumber++;     
            }
        }
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, List<int> taskCount) {
        createTasksInProjects(testData, taskCount, false) {
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, int taskCount, boolean addLabels) {  

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Создаем массив с количеством задач для каждого раздела
        List<int> arrayTaskCount = new ArrayList<>();
        for(int i = 0; i < createdProjectCount; i++) {
            arrayTaskCount.add(taskCount);
        }

        // Вызываем основной метод.
        createTasksInProjects(testData, arrayTaskCount, addLabels);
    }

    // === (перегрузка) Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, int taskCount) {
        createTasksInProjects(testData, taskCount, false)
    }

    // ==========================================================================================================================================================================
        


    
}
