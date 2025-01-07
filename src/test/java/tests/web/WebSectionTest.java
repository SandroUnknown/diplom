package tests.web;

import models.data.TestData;
import models.data.TestDataConfig;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;

public class WebSectionTest extends WebTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();

    // TODO : добавить тестов для других вариантов отображения
    
    @Test
    @DisplayName("Создать раздел в пустом проекте [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createSectionInEmptyProjectTest() {

        int templateNumber = 0;

        // TODO : ВАЖНО_1: Сделать, чтобы шаблон передавался (устанавливался) сразу через переменную whatIsCreate, а не отдельно.
        // TODO : ВАЖНО_2: В идеале и от переменной TestData testData = data.create() можно попробовать избавиться. Просто в build() для whatIsCreate прописать сразу создание всех сущностей.
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String url = testData.getProjects().get(0).getUrl();

        sectionPage
                .openPage(url)
                .login();
        
        sectionPage
                .inputSectionName(testSectionData.getName())
                .addSection();

        sectionPage
                .checkSuccsessfulCreatedSection(0, testSectionData.getName());


        sleep(3000);
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
        String url = testData.getProjects().get(0).getUrl();
        int sectionCountInProject = 0;
        for(SectionResponseModel section : testData.getSections()) {
            if (section.getProjectId().equals(projectId)) {
                sectionCountInProject++;
            }
        }
        
        sectionPage
                .openPage(url)
                .login();
        
        sectionPage
                .clickOnAddSection()
                .inputSectionName(testSectionData.getName())
                .addSection();

        sectionPage
                .checkSuccsessfulCreatedSection(sectionCountInProject, testSectionData.getName());

        sleep(3000);
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
        String url = testData.getProjects().get(0).getUrl();
        
        sectionPage
                .openPage(url)
                .login();
        
        sectionPage
                .clickOnSeparatorBetweenSections(separatorIndex)
                .inputSectionName(testSectionData.getName())
                .addSection();

        sleep(3000); // TODO : удалить

        sectionPage
                .checkSuccsessfulCreatedSection(separatorIndex + 1, testSectionData.getName());

        sleep(3000);
        // TODO : добавить API-проверку
    }

    @Test
    @DisplayName("Удалить раздел [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void deleteSectionTest() {

        int templateNumber = 0;
        int sectionNumberToDelete = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String url = testData.getProjects().get(0).getUrl();
        
        String sectionName = testData.getSections().get(sectionNumberToDelete).getName();

        sectionPage
                .openPage(url)
                .login();
        
        sectionPage
                .clickOtherActions(sectionNumberToDelete)
                .clickDeleteSectionButton()
                .clickConfirmDeleteSectionButtonElement();

        
        sectionPage
                .checkSuccsessfulDeleteSection(sectionName);

        sleep(3000);
        // TODO : добавить API-проверку

    }

    //TODO : сделать тест на драг энд дроп
    @Test
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }
}
