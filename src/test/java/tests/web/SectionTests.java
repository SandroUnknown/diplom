package tests.web;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import models.data.TestDataModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;

public class SectionTests extends WebTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();

    // TODO : добавить тестов для других вариантов отображения

    // TODO : убрать тег + добавить везде КлианАп
    @Tag("WEB_FOR_TEST")
    @Test
    @CleanupTestData
    @DisplayName("Создать раздел в пустом проекте [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createSectionInEmptyProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

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

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

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

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

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

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

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
    @Disabled
    @Test
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }
}
