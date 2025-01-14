package tests.web;

import data.DataCreator;
import enums.Color;
import enums.ViewStyle;
import helpers.annotations.CleanupTestData;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static enums.CheckField.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProjectTests extends WebTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.BERRY_RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    @Test
    @CleanupTestData
    @DisplayName("Создать проект (с заполнением только имени).")
    void createProjectWithNameOnlyTest() {

        projectPage
                .openPage()
                .login();

        // TODO : параметры надо откуда-то брать?
        projectPage
                .clickPlusButton()
                .clickAddProject()
                .inputProjectName(testProjectData.getName())
                .addProject();

        projectPage
                .checkProjectName(testProjectData.getName());

        sleep(1000); // TODO : нужный слип

        projectPage
                .uiCheckProject(testProjectData, NAME)
                .apiCheckProject(testProjectData, NAME);
    }

    @Test
    @CleanupTestData
    @DisplayName("Создать проект (с заполнением всех данных).")
    void createProjectWithFullDataTest() {

        projectPage
                .openPage()
                .login();

        // TODO : параметры надо откуда-то брать?
        projectPage
                .clickPlusButton()
                .clickAddProject()
                .inputProjectName(testProjectData.getName())
                .selectProjectColor(testProjectData.getColor())
                .addToFavorite(testProjectData.isFavorite())
                .selectProjectViewStyle(testProjectData.getViewStyle())
                .addProject();

        projectPage
                .fullCheckProject(testProjectData);

        sleep(1000); // TODO : нужный слип

        projectPage
                .uiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE)
                .apiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);
    }

    @Test
    @CleanupTestData
    @DisplayName("Удалить проект.")
    void deleteProjectTest() {

        int templateNumber = 0;

        new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        projectPage
                .openPage()
                .login();

        projectPage
                .mouseHoverOnCreatedProject()
                .clickOtherActions()
                .clickDeleteProjectButton()
                .clickConfirmDeleteProjectButtonElement();


        projectPage
                .checkDeleteProject();

        // TODO : слишком быстро проходит АПИ проверка, не успевают обновиться данные на сервере...
        sleep(1000);

        int projectCount = projectsApi.getAllProjects().size();
        assertThat(projectCount).isEqualTo(1);
    }
}
