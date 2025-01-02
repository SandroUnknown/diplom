package tests.web;

import enums.Color;
import enums.ViewStyle;
import helpers.annotations.WithLogin;
import models.projects.ProjectRequestModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;

public class FirstTest extends WebTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.BERRY_RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    @DisplayName("Создать проект.")
    @Test
    //@WithLogin
    void secondTest() {

        // TODO : сделать Опен в проджектПейдж
        authPage
                .login("/app/projects/active");

        // TODO : параметры надо откуда-то брать?
        projectPage
                .clickPlusButton()
                .clickAddProject()
                .inputProjectName(testProjectData.getName())
                .selectProjectColor(testProjectData.getColor())
                .addToFavorite(testProjectData.isFavorite())
                .selectViewStyle(testProjectData.getViewStyle())
                .addProject();

        projectPage
                .checkSuccessfulCreateProject(testProjectData);
    }
}
