package tests.api;

import helpers.annotations.CleanupTestData;
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

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);

        tasksApi.createNewTask(sectionId, "Task #1");

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Обновить задачу по ID.")
    void updateTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);

        tasksApi.updateTask(taskId, "ОБНОВЛЕННАЯ ЗАДАЧА");

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить задачу по ID.")
    void getTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);

        TaskResponseModel task = tasksApi.getTask(taskId);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить все активные задачи пользователя.")
    void getAllTasksTest() {

        List<String> projectsId = data.createProjects(2, 3);
        List<String> sectionsId = data.createSections(projectsId, 2, 3);
        data.createTasks(sectionsId, 2, 4);

        List<TaskResponseModel> tasks = tasksApi.getAllTasks();

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить все активные задачи (с применением фильтра `label`).")
    void getTasksWithFilterTest() {

        String labelId = data.createLabel();
        String labelName = labelsApi.getLabel(labelId).getName();
        List<String> projectsId = data.createProjects(2, 3);
        List<String> sectionsId = data.createSections(projectsId, 2, 3);
        List<String> tasksId = data.createTasks(sectionsId, 2, 4, labelName);

        HashMap<String, String> filters = new HashMap<>();
        filters.put("label", labelName);

        List<TaskResponseModel> tasks = tasksApi.getTasksWithFilter(filters);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Закрыть задачу по ID.")
    void closeTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);

        tasksApi.closeTask(taskId);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Снова открыть (закрытую) задачу по ID.")
    void reopenTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);
        tasksApi.closeTask(taskId);

        tasksApi.reopenTask(taskId);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Удалить задачу по ID.")
    void deleteTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);

        tasksApi.deleteTask(taskId);

        // TODO : сделать проверку
    }
}