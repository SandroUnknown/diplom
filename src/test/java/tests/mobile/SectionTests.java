package tests.mobile;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.projects.ProjectResponseModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя на ANDROID")
@Feature("Проверка разделов на ANDROID")
@Tags({@Tag("ANDROID"), @Tag("section")})
@DisplayName("Проверка разделов на ANDROID")
public class SectionTests extends MobileTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового раздела")
    @DisplayName("Создать новый раздел в пустом проекте [Только для варианта отображения проекта - ДОСКА (BOARD)]")
    void createSectionTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();
        ProjectResponseModel projectName = testData.getProjects().get(0);

        authScreen
                .login();
        bottomMenu
                .clickBrowse();
        browseScreen
                .openProject(projectName.getName());
        projectScreen
                .clickMoreOptions()
                .clickAddSection();
        createSectionModalWindow
                .inputSectionName(testSectionData.getName());

        projectScreen
                .checkSection(testSectionData.getName());

        step("Проверить, что раздел был корректно создан", () -> {
            SectionResponseModel myCreatedSection = sectionsApi.getAllSections(projectName.getId()).get(0);
            assertThat(myCreatedSection.getName()).isEqualTo(testSectionData.getName());
        });
    }
}