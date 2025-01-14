package tests.mobile;

import data.DataCreator;
import helpers.annotations.CleanupTestData;
import models.data.TestDataModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static enums.CheckField.CONTENT;
import static enums.CheckField.PRIORITY;

public class MobileTaskTests extends MobileTestBase {

    private final TaskRequestModel testTaskData = TaskRequestModel.builder()
            .content("НОВАЯ ЗАДАЧА")
            .priority(2)
            .build();

    @Tag("MOBILE_FOR_TEST")
    @Test
    @CleanupTestData
    @DisplayName("Создать задачу в пустом разделе [Только для варианта отображения проекта - ДОСКА (BOARD)].")
    void createSectionTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .create();
        String projectName = testData.getProjects().get(0).getName();
        String sectionName = testData.getSections().get(0).getName();

        authScreen
                .login();
        bottomMenu
                .clickBrowse();
        browseScreen
                .openProject(projectName);
        projectScreen
                .clickAddTask(sectionName);
        createTaskModalWindow
                .inputTaskName(testTaskData.getContent())
                .setTaskPriority(testTaskData.getPriority())
                .clickSubmit(true);

        projectScreen
                .clickOnTask(sectionName, testTaskData.getContent());
        taskEditScreen
                .checkTask(testTaskData, CONTENT, PRIORITY);


        // TODO : выполнить проверку API
    }
}