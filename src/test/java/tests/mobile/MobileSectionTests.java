package tests.mobile;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import models.data.TestDataModel;
import models.sections.SectionRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MobileSectionTests extends MobileTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
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
                .openProject(projectName);
        projectScreen
                .clickMoreOptions()
                .clickAddSection();
        createSectionModalWindow
                .inputSectionName(testSectionData.getName());

        projectScreen
                .checkSection(testSectionData.getName());

        // TODO : выполнить проверку API
    }
}