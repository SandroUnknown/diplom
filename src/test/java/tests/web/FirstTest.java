package tests.web;

import enums.Color;
import enums.ViewStyle;
import models.data.TestData;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;

public class FirstTest extends WebTestBase {

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
    }

    // TODO : доделать!
    @Test
    @DisplayName("Удалить проект.")
    void deleteProjectTest() {

        // Предварительно создать проект
        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);


        //String parentProjectId = testData.getProjects().get(0).getId();
        //testInnerProjectData.setParentId(parentProjectId);


        projectPage
                .openPage()
                .login();

        // TODO : параметры надо откуда-то брать?
        /*projectPage
                .clickPlusButton()
                .clickAddProject()
                .inputProjectName(testProjectData.getName())
                .selectProjectColor(testProjectData.getColor())
                .addToFavorite(testProjectData.isFavorite())
                .selectViewStyle(testProjectData.getViewStyle())
                .addProject();*/

        /*projectPage
                .fullCheckProject(testProjectData);*/

        sleep(5000);
    }
}
