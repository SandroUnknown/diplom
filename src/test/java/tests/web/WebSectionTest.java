package tests.web;

import enums.Color;
import enums.ViewStyle;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebSectionTest extends WebTestBase {

    private final SectionRequestModel testSectionData = SectionRequestModel.builder()
            .name("НОВЫЙ РАЗДЕЛ")
            .build();

    // TODO : проверить варианты отображения
    @Test
    @DisplayName("Создать раздел (когда в проекте изначально нет других разделов).")
    void createFirstSectionTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        data.create(templateNumber, whatIsCreate);
        
        sectionPage
                .openPage()
                .login();
        
        sectionPage
                .inputSectionName(testSectionData.getName())
                .addSection()

        // TODO : сделать провеку!!!
        /*sectionPage
                .fullCheckProject(testProjectData);*/
    }

    @Test
    @DisplayName("Создать раздел (когда в проекте изначально нет других разделов).")
    void createNotFirstSectionTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        data.create(templateNumber, whatIsCreate);
        
        sectionPage
                .openPage()
                .login();
        
        sectionPage
                .clickOnAddSection()
                .inputSectionName(testSectionData.getName())
                .addSection()

        
        // TODO : сделать провеку!!!
        /*sectionPage
                .fullCheckProject(testProjectData);*/
    }

    @Test
    @DisplayName("Удалить раздел.")
    void deleteSectionTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String sectionId = testData.getSections().get(0).getId();

        sectionPage
                .openPage()
                .login();
        
        sectionPage
                .clickOtherActions(sectionId);
                .clickDeleteSectionButton()
                .clickConfirmDeleteSectionButtonElement();

        
        /*
        sectionPage
                .checkDeleteProject();

        // TODO : слишком быстро проходит АПИ проверка, не успевают обновиться данные на сервере...
        sleep(1000);

        int projectCount = projectsApi.getAllProjects().size();
        assertThat(projectCount).isEqualTo(1);*/
    }
}
