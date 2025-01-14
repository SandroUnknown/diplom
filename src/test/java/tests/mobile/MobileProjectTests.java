package tests.mobile;

import enums.Color;
import enums.ViewStyle;
import helpers.annotations.CleanupTestData;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static enums.ProjectField.*;

public class MobileProjectTests extends MobileTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    @Tag("MOBILE_FOR_TEST")
    @Test
    @CleanupTestData
    void createProjectTest() {

        authScreen
                .login();
        bottomMenu
                .clickBrowse();
        browseScreen
                .clickCreateProject();
        editProjectScreen
                .inputProjectName(testProjectData.getName())
                .selectProjectColor(testProjectData.getColor())
                .addToFavorite(testProjectData.isFavorite())
                .selectProjectViewStyle(testProjectData.getViewStyle())
                .clickApplyButtonElement();

        projectScreen
                .clickEditProject();
        editProjectScreen
                .uiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);

        // TODO : выполнить проверку API
    }
}