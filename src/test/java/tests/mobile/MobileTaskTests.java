package tests.mobile;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static enums.CheckField.CONTENT;
import static enums.CheckField.PRIORITY;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя на ANDROID")
@Feature("Проверка задач на ANDROID")
@Tags({ @Tag("ANDROID"), @Tag("task") })
@DisplayName("Проверка задач на ANDROID")
public class MobileTaskTests extends MobileTestBase {

    private final TaskRequestModel testTaskData = TaskRequestModel.builder()
            .content("НОВАЯ ЗАДАЧА")
            .priority(2)
            .build();

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой задачи")
    @DisplayName("Создать новую задачу в пустом разделе [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void createTaskTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();
        String projectName = testData.getProjects().get(0).getName();
        String sectionName = testData.getSections().get(0).getName();

        authScreen
                .login();
        bottomMenu
                .clickBrowse();
        browseScreen
                .openProject(projectName);
        projectScreen
                .clickAddTask(sectionName);
        createTaskModalWindow
                .inputTaskName(testTaskData.getContent())
                .setTaskPriority(testTaskData.getPriority())
                .clickSubmit(true);

        projectScreen
                .clickOnTask(sectionName, testTaskData.getContent());
        taskEditScreen
                .checkTask(testTaskData, CONTENT, PRIORITY);

        // TODO : выполнить проверку API
    }
}