package tests.web;

import enums.Color;
import enums.ViewStyle;
import models.data.TestData;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebTaskTest extends WebTestBase {

    private final TaskRequestModel testTaskData = TaskRequestModel.builder()
            .content("НОВАЯ ЗАДАЧА")
            .priority(2)
            .build();

    // TODO : добавить тестов для других вариантов отображения?
    
    @Test
    @DisplayName("Создать задачу в пустом разделе [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createTaskInEmptySectionTest() {

        int templateNumber = 0;
        int taskNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
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

    /*@Test
    @DisplayName("Удалить задачу [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void deleteTaskTest() {

        int templateNumber = 0;
        int sectionNumberInProject = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
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
    @Test
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }
}
