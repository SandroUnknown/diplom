package tests;

import models.projects.ProjectRequestModel;
import models.sections.SectionRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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





}
