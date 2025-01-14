package tests.mobile;

import data.DataCreator;
import enums.Color;
import enums.ViewStyle;
import helpers.annotations.CleanupTestData;
import models.data.TestDataModel;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MobileSectionTests extends MobileTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    @Tag("MOBILE_FOR_TEST")
    @Test
    @CleanupTestData
    @DisplayName("Создать раздел в пустом проекте [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createSectionTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();
        String projectName = testData.getProjects().get(0).getName();

        authScreen
                .login();
        bottomMenu
                .clickBrowse();
        browseScreen
                .clickShowProject()
                .openProject(projectName);

        /*editProjectScreen
                .inputProjectName(testProjectData.getName())
                .selectProjectColor(testProjectData.getColor())
                .addToFavorite(testProjectData.isFavorite())
                .selectProjectViewStyle(testProjectData.getViewStyle())
                .clickApplyButtonElement();

        projectScreen
                .clickEditProject();
        editProjectScreen
                .uiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);*/

        // TODO : выполнить проверку API
    }
}