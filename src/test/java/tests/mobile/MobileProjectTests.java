package tests.mobile;

import enums.Color;
import enums.ViewStyle;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static enums.CheckField.*;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя на ANDROID")
@Feature("Проверка проектов на ANDROID")
@Tags({ @Tag("ANDROID"), @Tag("project") })
@DisplayName("Проверка проектов на ANDROID")
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
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового проекта")
    @DisplayName("Создать новый проект (с заполнением имени, цвета, отметкой 'избранное' и варианта отображения)")
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
                .checkProject(testProjectData, NAME, COLOR, FAVORITE, VIEW_STYLE);

        // TODO : выполнить проверку API
    }
}