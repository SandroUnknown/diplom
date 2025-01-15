package tests.web;

import data.DataCreator;
import enums.Color;
import enums.ViewStyle;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static enums.CheckField.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через WEB")
@Feature("Проверка проектов через WEB")
@Tags({ @Tag("WEB"), @Tag("project") })
@DisplayName("Проверка проектов через WEB")
public class ProjectTests extends WebTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.BERRY_RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    // TODO : убрать тег
    @Tag("WEB_FOR_TEST")
    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового проекта")
    @DisplayName("Создать новый проект (с заполнением только имени)")
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

        sleep(1000); // TODO : нужный слип

        projectPage
                .checkProject(testProjectData, NAME)
                .apiCheckProject(testProjectData, NAME);
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового проекта")
    @DisplayName("Создать новый проект (с заполнением имени, цвета, отметкой 'избранное' и варианта отображения)")
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

        sleep(1000); // TODO : нужный слип

        projectPage
                .checkProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE)
                .apiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление проекта")
    @DisplayName("Удалить проект по ID")
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
