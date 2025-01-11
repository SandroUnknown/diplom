package tests.mobile;

import enums.Color;
import enums.ViewStyle;
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
    void createProjectTest() {

        authScreen.login();

        bottomMenu.clickBrowse();
        browseScreen.clickCreateProject();
        editProjectScreen.inputProjectName(testProjectData.getName());
        editProjectScreen.selectProjectColor(testProjectData.getColor());
        editProjectScreen.addToFavorite(testProjectData.isFavorite());
        editProjectScreen.selectProjectViewStyle(testProjectData.getViewStyle());
        editProjectScreen.clickApplyButtonElement();

        // TODO : выполнить проверку UI
        browseScreen.clickEditProject();
        editProjectScreen.uiCheckProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);

        // TODO : выполнить проверку API
    }
}