package tests.api;

import models.data.TestData;
import models.data.TestDataConfig;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 40 сек / 6 тестов (разброс от 28 до 52 сек)

public class SectionsTest extends TestBase {

    @Test
    @DisplayName("[API] Создать новый раздел.")
    void createNewSectionsTest() {

        // Что создаем? - только проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String projectId = testData.getProjects().get(0).getId();

        // Подготавливаем данные для раздела
        SectionRequestModel sectionData = SectionRequestModel.builder()
                .projectId(projectId)
                .name("НОВЫЙ РАЗДЕЛ")
                .build();

        sectionsApi.createNewSection(sectionData);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Обновить раздел.")
    void updateSectionTest() {

        // Что создаем? - проект и раздел в проекте
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String sectionId = testData.getSections().get(0).getId();

        sectionsApi.updateSection(sectionId, "ОБНОВЛЁННЫЙ РАЗДЕЛ");

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все разделы внутри проекта.")
    void getAllSectionsInProjectTest() {

        // Что создаем? - проект и раздел в проекте
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // Получаем ID проекта
        String projectId = testData.getProjects().get(0).getId();

        List<SectionResponseModel> sections = sectionsApi.getAllSections(projectId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все разделы пользователя.")
    void getAllSectionsTest() {

        // Что создаем? - проект и раздел в проекте
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        List<SectionResponseModel> sections = sectionsApi.getAllSections();

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Удалить раздел по ID.")
    void deleteSectionTest() {

        // Что создаем? - проект и раздел в проекте
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID раздела
        String sectionId = testData.getSections().get(0).getId();

        sectionsApi.deleteSection(sectionId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Удалить все разделы в проекте.")
    void deleteAllSectionsInProjectTest() {

        // Что создаем? - проект и раздел в проекте
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // Получаем ID раздела
        String projectId = testData.getProjects().get(0).getId();

        // удалить все разделы в проекте
        sectionsApi.deleteAllSectionInProject(projectId);

        // TODO : сделать проверку
        System.out.println();
    }
}