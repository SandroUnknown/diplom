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

public class DataCreator3 {
/*
    // === Получаем названия всех созданных МЕТОК ===
    private List<String> getLabelsName() {
        
        List<String> labelName = new ArrayList<>();

        // Получаем количество созданных меток
        int createdLabelCount = testData.getLabels().size();

        for(int i = 1; i <= createdLabelCount) {
            labelName.add(String.format("Метка №%s", i));
        }
        
        return labelName;
    }

    // === Возвращает список случайных меток (количество, исходный список) ===
    private List<String> getRandomLabels(labelCount, labelName) {

        if (labelCount == 0 || labelName.size() == 0) {
            return new ArraList<>();
        }
        
        List<String> labelNameCopy = new ArrayList<>(labelName);
        List<String> newLabelNameList = new ArraList<>();

        for(int i = 0; i < labelCount; i++) {
            int randomIndex = random.nextInt(labelNameCopy.size());
            newLabelNameList.add(labelNameCopy.get(randomIndex));
            labelNameCopy.remove(randomIndex);
        }
        
        return newLabelNameList;
    }


    
    // ==========================================================================================================================================================================
    // === Создание МЕТОК ===
    public void createLabels(AccountData testData, int labelCount) {
        
        // Создаем метки
        for(int i = 0; i < labelCount; i++) {
            LabelRequestModel request = LabelRequestModel.builder()
                .name(String.format("Метка №%s", i + 1))
                .isFavorite(true)
                .color(Color.getRandom())    // TODO : сделать случайную генерацию
                .build();

            LabelResponseModel label = labelApi.createNewProject(request);
            testData.getLabels().add(label);
        }
    }
    // ==========================================================================================================================================================================

  
    // ==========================================================================================================================================================================
    // === Создание ПРОЕКТА в корне ===
    public void createProjects(AccountData testData, int projectCount) {    // локальная переменная с моими данными, количество проектов
        
        // Создаем проекты
        for(int i = 0; i < projectCount; i++) {
            ProjectRequestModel request = ProjectRequestModel.builder()
                .name(String.format("Проект №%s", i + 1))
                .color(Color.getRandom())    // TODO : сделать случайную генерацию
                .viewStyle(BOARD)
                .build();

            ProjectResponseModel project = projectApi.createNewProject(request);
            testData.getProjects().add(project);
        }
    }
    // ==========================================================================================================================================================================

    
    // ==========================================================================================================================================================================
    // === Создание РАЗДЕЛА в проекте ===
    public void createSections(AccountData testData, List<int> sectionCount) {
        
        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Создаем разделы
        for(int i = 0; i < createdProjectCount; i++) {
            String projectId = testData.getProjects().get(i).getId();
            for(int j = 0; j < sectionCount.get(i); j++) {
                SectionRequestModel request = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name(String.format("Раздел №%s для проекта №%s", j + 1, i + 1))
                    .build();

                SectionResponseModel section = sectionApi.createNewSection(request);
                testData.getSections().add(section);
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
    // === Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, List<int> taskCount, int labelCount) {

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Получаем имена ранее созданных меток
        List<String> labelName = new ArrayList<>();
        if (labelCount > 0) {
            labelName = getLabelsName();
            if (labelCount > labelName.size()) {
                // TODO : выбросить эксепшен, что я хочу добавить больше меток, чем я создал
            }
        }
        
        // Создаем задачи.
        for(int i = 0; i < createdProjectCount; i++) {
            String projectId = testData.getProjects().get(i).getId();
            for(int j = 0; j < taskCount.get(i); j++) {

                TaskRequestModel request = TaskRequestModel.builder()
                    .projectId(projectId)
                    .content(String.format("Задача №%s для проекта №%s", j + 1, i + 1))
                    .priority(new Random().nextInt(4) + 1)
                    .labels(getRandomLabels(labelCount, labelName))
                    .build();
                
                TaskResponseModel task = taskApi.createNewTask(request);
                testData.getTasks().add(task);   
            }
        }
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, List<int> taskCount) {
        createTasksInProjects(testData, taskCount, 0) {
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, int taskCount, int labelCount) {  

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Создаем массив с количеством задач для каждого раздела
        List<int> arrayTaskCount = new ArrayList<>();
        for(int i = 0; i < createdProjectCount; i++) {
            arrayTaskCount.add(taskCount);
        }

        // Вызываем основной метод.
        createTasksInProjects(testData, arrayTaskCount, labelCount);
    }

    // === (перегрузка) Создание ЗАДАЧИ в проекте ===
    public void createTasksInProjects(AccountData testData, int taskCount) {
        createTasksInProjects(testData, taskCount, 0)
    }

    // ==========================================================================================================================================================================

        
    // ==========================================================================================================================================================================
    // === Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, List<int> taskCount, int labelCount) {

        // Получаем количество разделов, которые мы уже создали.
        int createdSectionCount = testData.getSections().size();

        // Получаем имена ранее созданных меток
        List<String> labelName = new ArrayList<>();
        if (labelCount > 0) {
            labelName = getLabelsName();
            if (labelCount > labelName.size()) {
                // TODO : выбросить эксепшен, что я хочу добавить больше меток, чем я создал
            }
        }

        // Создаем задачи.
        for(int i = 0; i < createdSectionCount; i++) {
            String sectionId = testData.getSections().get(i).getId();
            for(int j = 0; j < taskCount.get(i); j++) {
                TaskRequestModel request = TaskRequestModel.builder()
                    .sectionId(sectionId)
                    .content(String.format("Задача №%s для раздела №%s", j + 1, i + 1))
                    .priority(new Random().nextInt(4) + 1)
                    .labels(getRandomLabels(labelCount, labelName))
                    .build();
                
                TaskResponseModel task = taskApi.createNewTask(request);
                testData.getTasks().add(task);
            }
        }
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, List<int> taskCount) {
        createTasksInSections(testData, taskCount, 0) {
    }
    
    // === (перегрузка) Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, int taskCount, int labelCount) {  

        // Получаем количество разделов, которые мы уже создали.
        int createdSectionCount = testData.getSections().size();

        // Создаем массив с количеством задач для каждого раздела
        List<int> arrayTaskCount = new ArrayList<>();
        for(int i = 0; i < createdSectionCount; i++) {
            arrayTaskCount.add(taskCount);
        }

        // Вызываем основной метод.
        createTasksInSections(testData, arrayTaskCount, labelCount);
    }

    // === (перегрузка) Создание ЗАДАЧИ в разделе ===
    public void createTasksInSections(AccountData testData, int taskCount) {
        createTasksInSections(testData, taskCount, 0)
    }

    // ==========================================================================================================================================================================


    // ==========================================================================================================================================================================
    // === Создание КОММЕНТОВ в проекте ===
    public void createCommentsInProjects(AccountData testData, List<int> commentCount) {

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Создаем комменты.
        for(int i = 0; i < createdProjectCount; i++) {     
            String projectId = testData.getProjects().get(i).getId();    
            for(int j = 0; j < commentCount.get(i); j++) {  
                CommentRequestModel request = CommentRequestModel.builder()
                    .projectId(projectId)
                    .content(String.format("Комментарий №%s для проекта №%s", j + 1, i + 1))    
                    .build();
                
                CommentResponseModel comment = commentApi.createNewComment(request);
                testData.getCommentsInProjects().add(comment);         
            }
        }
    }
    
    // === (перегрузка) Создание КОММЕНТОВ в проекте ===
    public void createCommentsInProjects(AccountData testData, int commentCount) {  

        // Получаем количество проектов, которые мы уже создали.
        int createdProjectCount = testData.getProjects().size();

        // Создаем массив с количеством задач для каждого раздела
        List<int> arrayCommentCount = new ArrayList<>();
        for(int i = 0; i < createdProjectCount; i++) {
            arrayCommentCount.add(commentCount);
        }

        // Вызываем основной метод.
        createCommentsInProjects(testData, arrayCommentCount);
    }


    // ==========================================================================================================================================================================


    // ==========================================================================================================================================================================
    // === Создание КОММЕНТОВ в задаче в проекте ===
    public void createCommentsInTaskInProjects(AccountData testData, List<int> commentCount) {

        // Получаем количество задач в проекте, которые мы уже создали.
        int createdTaskCount = testData.getTasksInProjects().size();

        // Создаем комменты.
        for(int i = 0; i < createdTaskCount; i++) {     
            String taskId = testData.getTasksInProjects().get(i).getId();    
            for(int j = 0; j < commentCount.get(i); j++) {  
                CommentRequestModel request = CommentRequestModel.builder()
                    .taskId(taskId)
                    .content(String.format("Комментарий №%s для задачи №%s", j + 1, i + 1))    
                    .build();
                
                CommentResponseModel comment = commentApi.createNewComment(request);
                testData.getCommentsInTaskInProjects().add(comment);         
            }
        }
    }
    
    // === (перегрузка) Создание КОММЕНТОВ в проекте ===
    public void createCommentsInTaskInProjects(AccountData testData, int commentCount) {  

        // Получаем количество задач в проекте, которые мы уже создали.
        int createdTaskCount = testData.getTasksInProjects().size();

        // Создаем массив с количеством задач для каждого проекта
        List<int> arrayCommentCount = new ArrayList<>();
        for(int i = 0; i < createdTaskCount; i++) {
            arrayCommentCount.add(commentCount);
        }

        // Вызываем основной метод.
        createCommentsInTaskInProjects(testData, arrayCommentCount);
    }


    // ==========================================================================================================================================================================


    // ==========================================================================================================================================================================
    // === Создание КОММЕНТОВ в задаче в разделе ===
    public void createCommentsInTaskInSections(AccountData testData, List<int> commentCount) {

        // Получаем количество задач в разделе, которые мы уже создали.
        int createdTaskCount = testData.getTasksInSections().size();

        // Создаем комменты.
        for(int i = 0; i < createdTaskCount; i++) {     
            String taskId = testData.getTasksInSections().get(i).getId();    
            for(int j = 0; j < commentCount.get(i); j++) {  
                CommentRequestModel request = CommentRequestModel.builder()
                    .taskId(taskId)
                    .content(String.format("Комментарий №%s для задачи №%s", j + 1, i + 1))    
                    .build();
                
                CommentResponseModel comment = commentApi.createNewComment(request);
                testData.getCommentsInTaskInSections().add(comment);         
            }
        }
    }
    
    // === (перегрузка) Создание КОММЕНТОВ в задаче в разделе ===
    public void createCommentsInTaskInSections(AccountData testData, int commentCount) {  

        // Получаем количество задач в разделе, которые мы уже создали.
        int createdTaskCount = testData.getTasksInSections().size();

        // Создаем массив с количеством задач для каждого проекта
        List<int> arrayCommentCount = new ArrayList<>();
        for(int i = 0; i < createdTaskCount; i++) {
            arrayCommentCount.add(commentCount);
        }

        // Вызываем основной метод.
        createCommentsInTaskInSections(testData, arrayCommentCount);
    }


    // ==========================================================================================================================================================================
*/
}
