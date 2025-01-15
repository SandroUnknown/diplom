package tests.web;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        //String projectId = testData.getProjects().get(0).getId();
        String url = testData.getProjects().get(0).getUrl();

        taskPage
                .openPage(url)
                .login();

        taskPage
                .clickOnAddTask(taskNumber)
                .inputTaskContent(testTaskData.getContent())
                .selectTaskPriority(String.valueOf(testTaskData.getPriority()))
                .addTask();

        sleep(3000);


        /*
        sectionPage
                .checkSuccsessfulCreatedSection(0, testSectionData.getName());*/

        // TODO : добавить UI-проверку
        
        // TODO : добавить API-проверку
    }

    /*
    @Test
    @CleanupTestData
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление задачи")
    @DisplayName("Удалить задачу [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void deleteTaskTest() {

        int templateNumber = 0;
        int sectionNumberInProject = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();
        
        String sectionId = testData.getSections().get(sectionNumberInProject).getId();
        String sectionName = testData.getSections().get(sectionNumberInProject).getName();

        taskPage
                .openPage(projectId)
                .login();

        taskPage
                .clickOtherActions(sectionId);
                .clickDeleteSectionButton()
                .clickConfirmDeleteSectionButtonElement();


        taskPage
                .checkSuccsessfulDeleteSection(sectionName);

        // TODO : добавить UI-проверку
        
        // TODO : добавить API-проверку

    }*/

    //TODO : сделать тест на драг энд дроп
    @Disabled
    @Test
    @CleanupTestData
    @Severity(SeverityLevel.MINOR)
    @Story("Перемещение задачи")
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }
}
