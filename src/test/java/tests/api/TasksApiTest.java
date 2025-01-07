package tests.api;

import data.DataCreator;
import models.data.TestDataModel;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 84 сек / 8 тестов (разброс от 71 до 97 сек)

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
    @DisplayName("[API] Создать новую задачу.")
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

        assertThat(myCreatedTask.getContent()).isEqualTo(testTaskData.getContent());
        assertThat(myCreatedTask.getPriority()).isEqualTo(testTaskData.getPriority());
    }

    @Test
    @DisplayName("[API] Обновить задачу по ID.")
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

        assertThat(myUpdatedTask.getContent()).isEqualTo(updatedTestTaskData.getContent());
        assertThat(myUpdatedTask.getPriority()).isEqualTo(updatedTestTaskData.getPriority());
    }

    @Test
    @DisplayName("[API] Получить задачу по ID.")
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

        assertThat(myReceivedTask.getContent()).isEqualTo(myCreatedTask.getContent());
        assertThat(myReceivedTask.getPriority()).isEqualTo(myCreatedTask.getPriority());
    }

    @Test
    @DisplayName("[API] Получить все активные задачи пользователя.")
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

        assertThat(myReceivedTasks.size()).isEqualTo(myCreatedTasks.size());
        for(int i = 0; i < myCreatedTasks.size(); i++) {
            assertThat(myReceivedTasks.get(i).getContent()).isEqualTo(myCreatedTasks.get(i).getContent());
            assertThat(myReceivedTasks.get(i).getPriority()).isEqualTo(myCreatedTasks.get(i).getPriority());
        }
    }

    @Test
    @DisplayName("[API] Получить все активные задачи (с применением фильтра `label`).")
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

        String labelName = testData.getLabels().get(1).getName(); // заменить на 0?

        List<TaskResponseModel> myCreatedTasks = new ArrayList<>();
        for(TaskResponseModel myCreatedTask : testData.getTasksInSections()) {
            if (myCreatedTask.getLabels().contains(labelName)) {
                myCreatedTasks.add(myCreatedTask);
            }
        }

        HashMap<String, String> filters = new HashMap<>();
        filters.put("label", labelName);

        List<TaskResponseModel> myReceivedTasks = tasksApi.getTasksWithFilter(filters);

        assertThat(myReceivedTasks.size()).isEqualTo(myCreatedTasks.size());
        for(int i = 0; i < myCreatedTasks.size(); i++) {
            assertThat(myReceivedTasks.get(i).getContent()).isEqualTo(myCreatedTasks.get(i).getContent());
            assertThat(myReceivedTasks.get(i).getPriority()).isEqualTo(myCreatedTasks.get(i).getPriority());
        }
    }

    @Test
    @DisplayName("[API] Закрыть задачу по ID.")
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

        TaskResponseModel myReceivedTasks = tasksApi.getTask(taskId);
        assertThat(myReceivedTasks.isCompleted()).isEqualTo(true);
    }

    @Test
    @DisplayName("[API] Снова открыть (закрытую) задачу по ID.")
    void reopenTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();
        tasksApi.closeTask(taskId);

        tasksApi.reopenTask(taskId);

        TaskResponseModel myReceivedTasks = tasksApi.getTask(taskId);
        assertThat(myReceivedTasks.isCompleted()).isEqualTo(false);
    }

    @Test
    @DisplayName("[API] Удалить задачу по ID.")
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

        int currentTaskCount = tasksApi.getAllTasks().size();
        assertThat(currentTaskCount).isEqualTo(createdTaskCount - 1);
    }
}