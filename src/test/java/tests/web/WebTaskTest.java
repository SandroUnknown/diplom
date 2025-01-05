package tests.web;

import enums.Color;
import enums.ViewStyle;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebTaskTest extends WebTestBase {

    private final TaskRequestModel testTaskData = TaskRequestModel.builder()
            .content("НОВАЯ ЗАДАЧА")
            .priority(4)
            .build();

    // TODO : добавить тестов для других вариантов отображения?
    
    @Test
    @DisplayName("Создать задачу в пустом разделе [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createTaskInEmptySectionTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();
        String sectionId = testData.getSections().get(0).getId();
        
        sectionPage
                .openPage(projectId)
                .login();
        
        sectionPage
                .clickOnAddTask(sectionId)
                .inputTaskContent(testTaskData.getContent())
                .selectTaskPriority(testTaskData.getPriority())
                .addTask();
        
        
        /*
        sectionPage
                .checkSuccsessfulCreatedSection(0, testSectionData.getName());*/

        // TODO : добавить UI-проверку
        
        // TODO : добавить API-проверку
    }

    @Test
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

        sectionPage
                .openPage(projectId)
                .login();
        
        sectionPage
                .clickOtherActions(sectionId);
                .clickDeleteSectionButton()
                .clickConfirmDeleteSectionButtonElement();

        
        sectionPage
                .checkSuccsessfulDeleteSection(sectionName);

        // TODO : добавить UI-проверку
        
        // TODO : добавить API-проверку

    }

    //TODO : сделать тест на драг энд дроп
    @Test
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }*/
}
