package tests.api;

import models.projects.ProjectRequestModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;
import static enums.Color.*;
import static enums.ViewStyle.BOARD;

public class SectionsApiTest extends TestBase {

    @Test
    @DisplayName("Создать новые разделы внутри проекта (API)")
    void createNewSectionsTest() {

        // Подготовка: Создаем новый проект.
        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project 101")
                .color(ORANGE)
                .viewStyle(BOARD)
                .build();

        String projectId = projectsApi.createNewProject(projectData).getId();

        // Создаем непосредственно разделы.
        for (int i = 1; i <= 3; i++) {
            SectionRequestModel sectionData = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name("Раздел №" + i)
                    .build();

            sectionsApi.createNewSection(sectionData);
        }
    }

    @Test
    @DisplayName("Создать новые разделы внутри проекта (API)")
    void updateSectionTest() {

        // TODO : Надо будет прошерстить этот тест. Написан он криво

        // Подготовка: Создаем новый проект.
        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project 102")
                .color(ORANGE)
                .viewStyle(BOARD)
                .build();

        String projectId = projectsApi.createNewProject(projectData).getId();

        // TODO : временный слип, чтобы увидеть, что ПРОЕКТ создался, и потом создается раздел...
        sleep(2000);

        // Создаем непосредственно разделы.
        // Первый
        SectionRequestModel sectionData1 = SectionRequestModel.builder()
                .projectId(projectId)
                .name("Новый раздел №1")
                .build();
        String sectionId1 = sectionsApi.createNewSection(sectionData1).getId();

        // Первый
        SectionRequestModel sectionData2 = SectionRequestModel.builder()
                .projectId(projectId)
                .name("Новый раздел №2")
                .build();
        String sectionId2 = sectionsApi.createNewSection(sectionData2).getId();

        // TODO : временный слип, чтобы увидеть, что РАЗДЕЛ создался, и потом бедет обновлен...
        sleep(2000);

        // Обновляем раздел.
        sectionsApi.updateSection(sectionId2, "ОБНОВЛЕННЫЙ раздел №2");

    }

    @Test
    @DisplayName("Удалить один раздел внутри проекта (API)")
    void deleteSectionTest() {

        // TODO : Надо будет прошерстить этот тест. Написан он криво

        // Подготовка: Создаем новый проект.
        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project 123")
                .color(ORANGE)
                .viewStyle(BOARD)
                .build();

        String projectId = projectsApi.createNewProject(projectData).getId();

        // Создаем непосредственно разделы.
        for (int i = 1; i <= 4; i++) {
            SectionRequestModel sectionData = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name("Новый раздел №" + i)
                    .build();
            sectionsApi.createNewSection(sectionData).getId();
        }

        // TODO : временный слип, чтобы увидеть, что РАЗДЕЛ создался, и потом бедет обновлен...
        sleep(2000);

        // Получаем все разделы (getAllSections() - все-все, getAllSectionsInProject(projectId) - по конкретному проекту).
        //List<SectionResponseModel> sections = sectionsApi.getAllSections();
        List<SectionResponseModel> sections = sectionsApi.getAllSectionsInProject(projectId);

        sectionsApi.deleteSection(sections.get(2).getId());

    }

    @Test
    @DisplayName("Удалить все разделы внутри проекта (API)")
    void deleteAllSectionsInProjectTest() {

        // TODO : Надо будет прошерстить этот тест. Написан он криво

        // Подготовка: Создаем новый проект.
        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project 150")
                .color(ORANGE)
                .viewStyle(BOARD)
                .build();

        String projectId = projectsApi.createNewProject(projectData).getId();

        // Создаем непосредственно разделы.
        for (int i = 1; i <= 4; i++) {
            SectionRequestModel sectionData = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name("Новый раздел №" + i)
                    .build();
            sectionsApi.createNewSection(sectionData).getId();
        }

        // TODO : временный слип, чтобы увидеть, что РАЗДЕЛ создался, и потом бедет обновлен...
        sleep(2000);

        // Получаем все разделы (getAllSections() - все-все, getAllSectionsInProject(projectId) - по конкретному проекту).
        //List<SectionResponseModel> sections = sectionsApi.getAllSections();
        //List<SectionResponseModel> sections = sectionsApi.getAllSectionsInProject(projectId);

        sectionsApi.deleteAllSectionInProject(projectId);

    }

    @Test
    @DisplayName("Получить все разделы внутри проекта (API)")
    void getAllSectionsTest() {

        // TODO : Надо будет прошерстить этот тест. Написан он криво

        // Подготовка: Создаем новый проект.
        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project 111")
                .color(ORANGE)
                .viewStyle(BOARD)
                .build();

        String projectId = projectsApi.createNewProject(projectData).getId();

        // TODO : временный слип, чтобы увидеть, что ПРОЕКТ создался, и потом создается раздел...
        //sleep(2000);

        // Создаем непосредственно разделы.
        for (int i = 1; i <= 3; i++) {
            SectionRequestModel sectionData = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name("Новый раздел №" + i)
                    .build();
            sectionsApi.createNewSection(sectionData).getId();
        }

        // TODO : временный слип, чтобы увидеть, что РАЗДЕЛ создался, и потом бедет обновлен...
        sleep(2000);

        // Получаем все разделы (getAllSections() - все-все, getAllSectionsInProject(projectId) - по конкретному проекту).
        List<SectionResponseModel> a = sectionsApi.getAllSections();
        //List<SectionResponseModel> a = sectionsApi.getAllSectionsInProject(projectId);

    }

}