package tests.api;

import data.DataCreator;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : может быть тест переменные передавать как параметры?

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через API")
@Feature("Проверка задач через API")
@Tags({ @Tag("API"), @Tag("task") })
@DisplayName("Проверка задач через API")
public class TasksApiTest extends ApiTestBase {

    private final TaskRequestModel testTaskData = TaskRequestModel.builder()
            .content("НОВАЯ ЗАДАЧА")
            .priority(4)
            .build();

    private final TaskRequestModel updatedTestTaskData = TaskRequestModel.builder()
            .content("ОБНОВЛЕННАЯ ЗАДАЧА")
            .priority(3)
            .build();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой задачи")
    @DisplayName("Создать новую задачу")
    void createNewTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        String sectionId = testData.getSections().get(0).getId();
        testTaskData.setSectionId(sectionId);

        TaskResponseModel myCreatedTask = tasksApi.createNewTask(testTaskData);

        step("Проверить, что задача была корректно создана", () -> {
            assertThat(myCreatedTask.getContent()).isEqualTo(testTaskData.getContent());
            assertThat(myCreatedTask.getPriority()).isEqualTo(testTaskData.getPriority());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Обновление задачи по ID")
    @DisplayName("Обновить задачу по ID")
    void updateTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();

        TaskResponseModel myUpdatedTask = tasksApi.updateTask(taskId, updatedTestTaskData);

        step("Проверить, что задача была корректно обновлена", () -> {
            assertThat(myUpdatedTask.getContent()).isEqualTo(updatedTestTaskData.getContent());
            assertThat(myUpdatedTask.getPriority()).isEqualTo(updatedTestTaskData.getPriority());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение задачи по ID")
    @DisplayName("Получить задачу по ID")
    void getTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        TaskResponseModel myCreatedTask = testData.getTasksInSections().get(0);
        String taskId = myCreatedTask.getId();

        TaskResponseModel myReceivedTask = tasksApi.getTask(taskId);

        step("Проверить, что задача была корректно получена", () -> {
            assertThat(myReceivedTask.getContent()).isEqualTo(myCreatedTask.getContent());
            assertThat(myReceivedTask.getPriority()).isEqualTo(myCreatedTask.getPriority());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех активных задач пользователя")
    @DisplayName("Получить все активные задачи пользователя")
    void getAllTasksTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        List<TaskResponseModel> myCreatedTasks = testData.getTasksInSections();

        List<TaskResponseModel> myReceivedTasks = tasksApi.getAllTasks();

        step("Проверить, что задачи были корректно получены", () -> {
            assertThat(myReceivedTasks.size()).isEqualTo(myCreatedTasks.size());
            for(int i = 0; i < myCreatedTasks.size(); i++) {
                assertThat(myReceivedTasks.get(i).getContent()).isEqualTo(myCreatedTasks.get(i).getContent());
                assertThat(myReceivedTasks.get(i).getPriority()).isEqualTo(myCreatedTasks.get(i).getPriority());
            }
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех активных задач пользователя (с применением фильтра `label`)")
    @DisplayName("Получить все активные задачи пользователя (с применением фильтра `label`)")
    void getTasksWithFilterTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .addLabelsForTasksInSections(true)
                .create();

        String labelName = testData.getLabels().get(1).getName(); // TODO : заменить на 0?

        List<TaskResponseModel> myCreatedTasks = new ArrayList<>();
        for(TaskResponseModel myCreatedTask : testData.getTasksInSections()) {
            if (myCreatedTask.getLabels().contains(labelName)) {
                myCreatedTasks.add(myCreatedTask);
            }
        }

        HashMap<String, String> filters = new HashMap<>();
        filters.put("label", labelName);

        List<TaskResponseModel> myReceivedTasks = tasksApi.getTasksWithFilter(filters);

        step("Проверить, что задачи были корректно получены", () -> {
            assertThat(myReceivedTasks.size()).isEqualTo(myCreatedTasks.size());
            for(int i = 0; i < myCreatedTasks.size(); i++) {
                assertThat(myReceivedTasks.get(i).getContent()).isEqualTo(myCreatedTasks.get(i).getContent());
                assertThat(myReceivedTasks.get(i).getPriority()).isEqualTo(myCreatedTasks.get(i).getPriority());
            }
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Закрытие задачи по ID")
    @DisplayName("Закрыть задачу по ID")
    void closeTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();

        tasksApi.closeTask(taskId);

        step("Проверить, что задача действительно была закрыта", () -> {
            TaskResponseModel myReceivedTasks = tasksApi.getTask(taskId);
            assertThat(myReceivedTasks.isCompleted()).isEqualTo(true);
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Открытие (ранее закрытой) задачи по ID")
    @DisplayName("Открыть (ранее закрытую) задачу по ID")
    void reopenTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();
        tasksApi.closeTask(taskId); // TODO : может создать уже готовый проект с закрытой задачей?

        tasksApi.reopenTask(taskId);

        step("Проверить, что задача действительно была открыта", () -> {
            TaskResponseModel myReceivedTasks = tasksApi.getTask(taskId);
            assertThat(myReceivedTasks.isCompleted()).isEqualTo(false);
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление задачи по ID")
    @DisplayName("Удалить задачу по ID")
    void deleteTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        int createdTaskCount = testData.getTasksInSections().size();
        String taskId = testData.getTasksInSections().get(0).getId();

        tasksApi.deleteTask(taskId);

        step("Проверить, что задача действительно была удалена", () -> {
            int currentTaskCount = tasksApi.getAllTasks().size();
            assertThat(currentTaskCount).isEqualTo(createdTaskCount - 1);
        });
    }
}
