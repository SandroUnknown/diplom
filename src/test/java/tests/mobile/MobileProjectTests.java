package tests.mobile;

import enums.Color;
import enums.ViewStyle;
import helpers.annotations.CleanupTestData;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static enums.CheckField.*;

@Tags({ @Tag("ANDROID"), @Tag("project") })
public class MobileProjectTests extends MobileTestBase {

    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ ПРОЕКТ")
            .color(Color.RED)
            .isFavorite(true)
            .viewStyle(ViewStyle.BOARD)
            .build();

    // TODO : убрать тег
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
        projectEditScreen
                .inputProjectName(testProjectData.getName())
                .selectProjectColor(testProjectData.getColor())
                .addToFavorite(testProjectData.isFavorite())
                .selectProjectViewStyle(testProjectData.getViewStyle())
                .clickApplyButtonElement();

        projectScreen
                .clickMoreOptions()
                .clickEditProject();
        projectEditScreen
                .сheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);

        // TODO : выполнить проверку API
    }
}