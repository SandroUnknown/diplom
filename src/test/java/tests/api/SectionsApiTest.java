package tests.api;

import data.DataCreator;
import models.data.TestDataModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через API")
@Feature("Проверка разделов через API")
@Tags({ @Tag("API"), @Tag("section") })
@DisplayName("Проверка разделов через API")
public class SectionsApiTest extends ApiTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();
    private final String updatedSectionName = "ОБНОВЛЁННЫЙ РАЗДЕЛ";

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового раздела")
    @DisplayName("Создать новый раздел")
    void createNewSectionsTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();
        testSectionData.setProjectId(projectId);

        SectionResponseModel myCreatedSection = sectionsApi.createNewSection(testSectionData);

        assertThat(myCreatedSection.getName()).isEqualTo(testSectionData.getName());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Обновление раздела")
    @DisplayName("Обновить раздел")
    void updateSectionTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        String sectionId = testData.getSections().get(0).getId();

        SectionResponseModel myUpdatedSection = sectionsApi.updateSection(sectionId, updatedSectionName);

        assertThat(myUpdatedSection.getName()).isEqualTo(updatedSectionName);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех разделов внутри проекта")
    @DisplayName("Получить все разделы внутри проекта")
    void getAllSectionsInProjectTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();

        List<SectionResponseModel> myCreatedSections = new ArrayList<>();
        for(SectionResponseModel myCreatedSection : testData.getSections()) {
            if (myCreatedSection.getProjectId().equals(projectId)) {
                myCreatedSections.add(myCreatedSection);
            }
        }

        List<SectionResponseModel> myReceivedSections = sectionsApi.getAllSections(projectId);

        assertThat(myReceivedSections.size()).isEqualTo(myCreatedSections.size());
        for(int i = 0; i < myCreatedSections.size(); i++) {
            assertThat(myReceivedSections.get(i).getName()).isEqualTo(myCreatedSections.get(i).getName());
        }
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех разделов пользователя")
    @DisplayName("Получить все разделы пользователя")
    void getAllSectionsTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        List<SectionResponseModel> myCreatedSections = testData.getSections();

        List<SectionResponseModel> myReceivedSections = sectionsApi.getAllSections();

        assertThat(myReceivedSections.size()).isEqualTo(myCreatedSections.size());
        for(int i = 0; i < myCreatedSections.size(); i++) {
            assertThat(myReceivedSections.get(i).getName()).isEqualTo(myCreatedSections.get(i).getName());
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление раздела по ID")
    @DisplayName("Удалить раздел по ID")
    void deleteSectionTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        int createdSectionCount = testData.getSections().size();
        String sectionId = testData.getSections().get(0).getId();

        sectionsApi.deleteSection(sectionId);

        int currentSectionCount = sectionsApi.getAllSections().size();
        assertThat(currentSectionCount).isEqualTo(createdSectionCount - 1);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление всех разделов в проекте")
    @DisplayName("Удалить все разделы в проекте")
    void deleteAllSectionsInProjectTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();

        sectionsApi.deleteAllSectionInProject(projectId);

        int currentSectionCount = sectionsApi.getAllSections(projectId).size();
        assertThat(currentSectionCount).isEqualTo(0);
    }
}
