package tests.api;

import models.tasks.TaskResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;

public class TasksApiTest extends TestBase {

    @Test
    @DisplayName("Создать новую задачу (API)")
    void createNewTaskTest() {

        // Создаем проект.
        String projectId = projectsApi.createNewProject("Project #1 for GET TASKS test").getId();

        // Создаем в проекте раздел.
        String sectionId = sectionsApi.createNewSection(projectId, "Section #1").getId();

        // Создаем в этом разделе задачу.
        tasksApi.createNewTask(sectionId, "Task #1");
    }

    @Test
    @DisplayName("Получить задачу по ID (API)")
    void getTaskTest() {

        // Создаем проект.
        String projectId = projectsApi.createNewProject("Project #1 for GET TASKS test").getId();

        // Создаем в проекте раздел.
        String sectionId = sectionsApi.createNewSection(projectId, "Section #1").getId();

        // Создаем в этом разделе задачу.
        String taskId = tasksApi.createNewTask(sectionId, "Task #1").getId();

        // TODO : Надо будет прошерстить этот тест. Написан он криво
        // Получаем задачу по ID
        TaskResponseModel task = tasksApi.getTask(taskId);
    }

    @Test
    @DisplayName("Получить все активные задачи (API)")
    void getAllTasksTest() {

        // Создаем несколько проектов.
        int projectCount = 2;
        for (int i = 1; i <= projectCount; i++) {
            String projectId = projectsApi.createNewProject("Project #" + i + " for GET TASKS test").getId();

            // Создаем в каждом проекте несколько разделов.
            int sectionCount = 2;
            for (int j = 1; j <= sectionCount; j++) {
                String sectionId = sectionsApi.createNewSection(projectId, "Section #" + j).getId();

                // Создаем в каждом разделе несколько задач.
                int taskCount = 2;
                for (int k = 1; k <= taskCount; k++) {
                    tasksApi.createNewTask(sectionId, "Task #" + k);
                }
            }
        }

        // TODO : Надо будет прошерстить этот тест. Написан он криво
        // Получаем все задачи в аккаунте
        List<TaskResponseModel> tasks = tasksApi.getAllTasks();

        System.out.println();

    }

    @Test
    @DisplayName("Получить все активные задачи, используя фильтрацию (API)")
    void getTasksWithFilterTest() {

        String projectId = projectsApi.getAllProjects().get(1).getId();
        String sectionId = sectionsApi.getAllSections(projectId).get(1).getId();
        String label = "xxx2";
        String filter = "@xxx3 & @NEW";

        // Получаем все задачи в аккаунте
        List<TaskResponseModel> tasks = tasksApi.getAllTasks();
        int i = 0;
        String tasksId = "";

        // Берем ID только четных
        for (TaskResponseModel task : tasks) {
            if (i % 2 == 0) {
                tasksId += task.getId() + ",";
            }
            i++;
        }
        tasksId = tasksId.substring(0, tasksId.length() - 1);

        HashMap<String, String> filters = new HashMap<>();
        //filters.put("filter", filter);
        filters.put("ids", tasksId);
        //filters.put("project_id", projectId);
        //filters.put("section_id", sectionId);
        //filters.put("label", label);

        tasks = tasksApi.getTasksWithFilter(filters);

        System.out.println();

    }

    @Test
    @DisplayName("Обновить задачу по ID (API)")
    void updateTaskTest() {

        // Создаем проект.
        String projectId = projectsApi.createNewProject("Project #1 for GET TASKS test").getId();

        // Создаем в проекте раздел.
        String sectionId = sectionsApi.createNewSection(projectId, "Section #1").getId();

        // Создаем в этом разделе задачу.
        String taskId = tasksApi.createNewTask(sectionId, "Task #1").getId();

        //sleep(2000);

        // TODO : Надо будет прошерстить этот тест. Написан он криво
        // Обновляем задачу
        tasksApi.updateTask(taskId, "Task #1 - UPDATED");
    }

    @Test
    @DisplayName("Закрыть задачу по ID (API)")
    void closeTaskTest() {

        // Создаем проект.
        String projectId = projectsApi.createNewProject("Project #1 for GET TASKS test").getId();

        // Создаем в проекте раздел.
        String sectionId = sectionsApi.createNewSection(projectId, "Section #1").getId();

        // Создаем в этом разделе задачу.
        String taskId = tasksApi.createNewTask(sectionId, "Task #1").getId();

        // TODO : Убрать при релизе
        sleep(2000);

        // Закрываем задачу
        tasksApi.closeTask(taskId);
    }

    @Test
    @DisplayName("Снова открыть задачу по ID (API)")
    void reopenTaskTest() {

        // Создаем проект.
        String projectId = projectsApi.createNewProject("Project #1 for GET TASKS test").getId();

        // Создаем в проекте раздел.
        String sectionId = sectionsApi.createNewSection(projectId, "Section #1").getId();

        // Создаем в этом разделе задачу.
        String taskId = tasksApi.createNewTask(sectionId, "Task #1").getId();

        // TODO : Убрать при релизе
        sleep(2000);

        // Закрываем задачу
        tasksApi.closeTask(taskId);

        // TODO : Убрать при релизе
        sleep(2000);

        // Закрываем задачу
        tasksApi.reopenTask(taskId);
    }

    @Test
    @DisplayName("Удалить задачу по ID (API)")
    void deleteTaskTest() {

        // Создаем проект.
        String projectId = projectsApi.createNewProject("Project #1 for GET TASKS test").getId();

        // Создаем в проекте раздел.
        String sectionId = sectionsApi.createNewSection(projectId, "Section #1").getId();

        // Создаем в этом разделе задачу.
        String taskId = tasksApi.createNewTask(sectionId, "Task #1").getId();

        // TODO : Убрать при релизе
        sleep(2000);

        // Закрываем задачу
        tasksApi.deleteTask(taskId);
    }
}