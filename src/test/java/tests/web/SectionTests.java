package tests.web;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.sleep;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через WEB")
@Feature("Проверка разделов через WEB")
@Tags({ @Tag("WEB"), @Tag("section") })
@DisplayName("Проверка разделов через WEB")
public class SectionTests extends WebTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового раздела")
    @DisplayName("Создать новый раздел в пустом проекте [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void createSectionInEmptyProjectTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                //.setTemplate(TEMPLATES_2.get(templateNumber)) // TODO : удалить
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
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового раздела")
    @DisplayName("Создать новый раздел в конце списка (когда в проекте уже имеется минимум 1 созданный раздел) [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void createNotFirstSectionInProjectAtEndOfListTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                //.setTemplate(TEMPLATES_2.get(templateNumber)) // TODO : удалить
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

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового раздела")
    @DisplayName("Создать новый раздел между двумя ранее созданными разделами [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void createSectionBetweenTwoPreviouslyCreatedSectionsTest() {

        //int templateNumber = 1; // TODO : удалить
        int templateNumber = 2;
        TestDataModel testData = new DataCreator.Setup()
                //.setTemplate(TEMPLATES_2.get(templateNumber)) // TODO : удалить
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();
        String url = testData.getProjects().get(0).getUrl();
        int separatorIndex = 0;
        
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
    @CleanupTestData
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление раздела")
    @DisplayName("Удалить раздел [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void deleteSectionTest() {

        int sectionNumberToDelete = 0;
        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                //.setTemplate(TEMPLATES_2.get(templateNumber))  // TODO : удалить
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
    @CleanupTestData
    @Severity(SeverityLevel.MINOR)
    @Story("Перемещение раздела")
    @DisplayName("ДРАГ энд ДРОП [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void dragAndDropSectionTest() {
    }
}
