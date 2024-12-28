package tests.api;

import models.data.TestData;
import models.data.TestDataConfig;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 84 сек / 8 тестов (разброс от 71 до 97 сек)

public class TasksTest extends TestBase {

    @Test
    @DisplayName("[API] Создать новую задачу.")
    void createNewTaskTest() {

        // Что создаем? - только проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String sectionId = testData.getSections().get(0).getId();

        // Подготавливаем данные для задачи
        TaskRequestModel taskData = TaskRequestModel.builder()
                .sectionId(sectionId)
                .content("НОВАЯ ЗАДАЧА")
                .priority(4)
                .build();

        // Создаем новую задачу
        tasksApi.createNewTask(taskData);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Обновить задачу по ID.")
    void updateTaskTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Подготавливаем данные для задачи
        TaskRequestModel updateTaskData = TaskRequestModel.builder()
                .content("ОБНОВЛЕННАЯ ЗАДАЧА")
                .priority(4)
                .build();

        // Создаем новую задачу
        tasksApi.updateTask(taskId, updateTaskData);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить задачу по ID.")
    void getTaskTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Получаем задачу по айди
        TaskResponseModel task = tasksApi.getTask(taskId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все активные задачи пользователя.")
    void getAllTasksTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Получаем все задачи пользователя
        List<TaskResponseModel> tasks = tasksApi.getAllTasks();

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все активные задачи (с применением фильтра `label`).")
    void getTasksWithFilterTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .addLabelsForTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // Получаем ID проекта
        String labelName = testData.getLabels().get(1).getName();

        // Создаем фильтр
        HashMap<String, String> filters = new HashMap<>();
        filters.put("label", labelName);

        // Получаем все задачи пользователя с фильтром
        List<TaskResponseModel> tasks = tasksApi.getTasksWithFilter(filters);

        // TODO : сделать проверку
        System.out.println();

    }

    @Test
    @DisplayName("[API] Закрыть задачу по ID.")
    void closeTaskTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Закрываем задачу
        tasksApi.closeTask(taskId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Снова открыть (закрытую) задачу по ID.")
    void reopenTaskTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Закрываем задачу
        tasksApi.closeTask(taskId);

        // открываем
        tasksApi.reopenTask(taskId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Удалить задачу по ID.")
    void deleteTaskTest() {

        // Что создаем? - проект, раздел и задачу
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Удаляем задачу
        tasksApi.deleteTask(taskId);

        // TODO : сделать проверку
        System.out.println();
    }
}