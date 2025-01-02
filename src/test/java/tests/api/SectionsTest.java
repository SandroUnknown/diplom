package tests.api;

import models.data.TestData;
import models.data.TestDataConfig;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 40 сек / 6 тестов (разброс от 28 до 52 сек)

public class SectionsTest extends TestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();
    private final String updatedSectionName = "ОБНОВЛЁННЫЙ РАЗДЕЛ";

    @Test
    @DisplayName("[API] Создать новый раздел.")
    void createNewSectionsTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();
        testSectionData.setProjectId(projectId);

        SectionResponseModel myCreatedSection = sectionsApi.createNewSection(testSectionData);

        assertThat(myCreatedSection.getName()).isEqualTo(testSectionData.getName());
    }

    @Test
    @DisplayName("[API] Обновить раздел.")
    void updateSectionTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String sectionId = testData.getSections().get(0).getId();

        SectionResponseModel myUpdatedSection = sectionsApi.updateSection(sectionId, updatedSectionName);

        assertThat(myUpdatedSection.getName()).isEqualTo(updatedSectionName);
    }

    @Test
    @DisplayName("[API] Получить все разделы внутри проекта.")
    void getAllSectionsInProjectTest() {

        int templateNumber = 1;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
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
    @DisplayName("[API] Получить все разделы пользователя.")
    void getAllSectionsTest() {

        int templateNumber = 1;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        List<SectionResponseModel> myCreatedSections = testData.getSections();

        List<SectionResponseModel> myReceivedSections = sectionsApi.getAllSections();

        assertThat(myReceivedSections.size()).isEqualTo(myCreatedSections.size());
        for(int i = 0; i < myCreatedSections.size(); i++) {
            assertThat(myReceivedSections.get(i).getName()).isEqualTo(myCreatedSections.get(i).getName());
        }
    }

    @Test
    @DisplayName("[API] Удалить раздел по ID.")
    void deleteSectionTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        int createdSectionCount = testData.getSections().size();
        String sectionId = testData.getSections().get(0).getId();

        sectionsApi.deleteSection(sectionId);

        int currentSectionCount = sectionsApi.getAllSections().size();
        assertThat(currentSectionCount).isEqualTo(createdSectionCount - 1);
    }

    @Test
    @DisplayName("[API] Удалить все разделы в проекте.")
    void deleteAllSectionsInProjectTest() {

        int templateNumber = 1;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();

        sectionsApi.deleteAllSectionInProject(projectId);

        int currentSectionCount = sectionsApi.getAllSections(projectId).size();
        assertThat(currentSectionCount).isEqualTo(0);
    }
}