package tests.web;

import data.DataCreator;
import enums.Color;
import enums.ViewStyle;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebProjectsTest extends WebTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.BERRY_RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    @Test
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

        // TODO : нужна АПИ-проверка
        /*step("Проверить имя созданного проекта (API)", () -> {
            String responseName = projectsApi.getAllProjects().get(0).getName();
            assertThat(projectName).isEqualTo(responseName);
        });*/

        projectPage
                .uiCheckProject(testProjectData, NAME)
                .apiCheckProject(testProjectData, NAME);
    }

    @Test
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
                .selectViewStyle(testProjectData.getViewStyle())
                .addProject();

        projectPage
                .fullCheckProject(testProjectData);

        // TODO : нужна АПИ-проверка 
        /*step("Проверить, что проект был корректно создан (API)", () -> {
            ProjectResponseModel project = projectsApi.getAllProjects().get(0);
            assertThat(testProjectData.getName()).isEqualTo(project.getName());
            assertThat(testProjectData.getColor()).isEqualTo(project.getColor());
            assertThat(testProjectData.isFavorite()).isEqualTo(project.isFavorite());
            assertThat(testProjectData.getViewStyle()).isEqualTo(project.getViewStyle());
        });*/

        projectPage
                .uiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE)
                .apiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);
    }

    @Test
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
