package tests.web;

import enums.Color;
import enums.ViewStyle;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebSectionTest extends WebTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();

    // TODO : добавить тестов для других вариантов отображения
    
    @Test
    @DisplayName("Создать раздел в пустом проекте [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createFirstSectionInProjectTest() {

        int templateNumber = 0;

        // TODO : ВАЖНО_1: Сделать, чтобы шаблон передавался (устанавливался) сразу через переменную whatIsCreate, а не отдельно.
        // TODO : ВАЖНО_2: В идеале и от переменной TestData testData = data.create() можно попробовать избавиться. Просто в build() для whatIsCreate прописать сразу создание всех сущностей.
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();
        
        sectionPage
                .openPage(projectId)
                .login();
        
        sectionPage
                .inputSectionName(testSectionData.getName())
                .addSection()

        sectionPage
                .checkSuccsessfulCreatedSection(0, testSectionData.getName());
        
        // TODO : добавить API-проверку
    }

    @Test
    @DisplayName("Создать раздел в конце списка (когда в проекте уже имеется минимум 1 созданный раздел) [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createNotFirstSectionInProjectAtEndOfListTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();
        int sectionCountInProject;
        for(ResponseSectionModel section : testData.getSections()) {
            if (section.getProjectId().equals(projectId)) {
                sectionCountInProject++;
            }
        }
        
        sectionPage
                .openPage(projectId)
                .login();
        
        sectionPage
                .clickOnAddSection()
                .inputSectionName(testSectionData.getName())
                .addSection()

        sectionPage
                .checkSuccsessfulCreatedSection(sectionCountInProject, testSectionData.getName());
        
        // TODO : добавить API-проверку
    }

    // TODO : проверить работоспособность!
    @Test
    @DisplayName("Создать раздел между двумя ранее созданными разделами [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createSectionBetweenTwoPreviouslyCreatedSectionsTest() {

        int templateNumber = 1;
        int separatorIndex = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();
        
        sectionPage
                .openPage(projectId)
                .login();
        
        sectionPage
                .clickOnSeparatorBetweenSections(separatorIndex)
                .inputSectionName(testSectionData.getName())
                .addSection()

        sectionPage
                .checkSuccsessfulCreatedSection(separatorIndex + 1, testSectionData.getName());

        // TODO : добавить API-проверку
    }

    @Test
    @DisplayName("Удалить раздел [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void deleteSectionTest() {

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

        // TODO : добавить API-проверку

    }

    //TODO : сделать тест на драг энд дроп
    @Test
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }
}
