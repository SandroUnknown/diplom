package tests.api;

import helpers.annotations.CleanupTestData;
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

        String projectId = data.createProject();

        SectionRequestModel sectionData = SectionRequestModel.builder()
                .projectId(projectId)
                .name("НОВЫЙ РАЗДЕЛ")
                .build();

        sectionsApi.createNewSection(sectionData);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Обновить раздел.")
    void updateSectionTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);

        //sleep(3000); // TODO : убрать перед релизом
        sectionsApi.updateSection(sectionId, "ОБНОВЛЁННЫЙ РАЗДЕЛ");

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить все разделы внутри проекта.")
    void getAllSectionsInProjectTest() {

        String projectId = data.createProject();
        data.createSections(projectId, 3, 5);

        //sleep(3000); // TODO : убрать перед релизом
        List<SectionResponseModel> sections = sectionsApi.getAllSections(projectId);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить все разделы пользователя.")
    void getAllSectionsTest() {

        List<String> projectsId = data.createProjects(2, 3);
        data.createSections(projectsId, 3, 5);

        //sleep(3000); // TODO : убрать перед релизом
        List<SectionResponseModel> sections = sectionsApi.getAllSections();

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Удалить раздел по ID.")
    void deleteSectionTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);

        //sleep(3000); // TODO : убрать перед релизом
        sectionsApi.deleteSection(sectionId);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Удалить все разделы в проекте.")
    void deleteAllSectionsInProjectTest() {

        String projectId = data.createProject();
        data.createSections(projectId, 4, 6);

        //sleep(3000); // TODO : убрать перед релизом
        sectionsApi.deleteAllSectionInProject(projectId);

        // TODO : сделать проверку
    }
}