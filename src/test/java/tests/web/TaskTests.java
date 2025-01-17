package tests.web;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.sleep;
import static enums.CheckField.*;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через WEB")
@Feature("Проверка задач через WEB")
@Tags({ @Tag("WEB"), @Tag("task") })
@DisplayName("Проверка задач через WEB")
public class TaskTests extends WebTestBase {

    private final TaskRequestModel testTaskData = TaskRequestModel.builder()
            .content("НОВАЯ ЗАДАЧА")
            .priority(2)
            .build();

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой задачи")
    @DisplayName("Создать новую задачу в пустом разделе [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void createTaskInEmptySectionTest() {

        int taskNumber = 0;
        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();
        String url = testData.getProjects().get(0).getUrl();

        taskPage
                .openPage(url)
                .login();
        taskPage
                .clickOnAddTask(taskNumber)
                .inputTaskContent(testTaskData.getContent())
                .selectTaskPriority(String.valueOf(testTaskData.getPriority()))
                .addTask();

        sleep(500); // TODO : вероятно нужный слип

        taskPage
                .checkTask(testTaskData, CONTENT, PRIORITY);
        tasksApi
                .checkTask(testTaskData, CONTENT, PRIORITY);
    }

    @Disabled
    @Test
    @CleanupTestData
    @Severity(SeverityLevel.MINOR)
    @Story("Перемещение задачи на новое место (Drag & Drop)")
    @DisplayName("Переместить задачу на новое место (Drag & Drop) [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void dragAndDropTaskTest() {
    }
}
