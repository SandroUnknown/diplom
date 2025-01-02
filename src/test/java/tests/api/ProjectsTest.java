package tests.api;

import models.data.TestData;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static enums.Color.*;
import static enums.ViewStyle.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 17 сек / 5 тестов

public class ProjectsTest extends TestBase {
        
    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
                .name("НОВЫЙ ПРОЕКТ")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();

    private final ProjectRequestModel testInnerProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ вложенный ПРОЕКТ")
            .color(GREEN)
            .viewStyle(BOARD)
            .build();

    private final ProjectRequestModel updatedTestProjectData = ProjectRequestModel.builder()
                .name("ОБНОВЛЁННЫЙ ПРОЕКТ")
                .color(RED)
                .isFavorite(true)
                .viewStyle(LIST)
                .build();
    
    @Test
    @DisplayName("[API] Создать новый проект.")
    void createNewProjectTest() {

        ProjectResponseModel myCreatedProject = projectsApi.createNewProject(testProjectData);

        assertThat(myCreatedProject.getName()).isEqualTo(testProjectData.getName());
        assertThat(myCreatedProject.getColor()).isEqualTo(testProjectData.getColor());
        assertThat(myCreatedProject.getViewStyle()).isEqualTo(testProjectData.getViewStyle());
    }

    @Test
    @DisplayName("[API] Создать новый вложенный проект.")
    void createNewProjectInProjectTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String parentProjectId = testData.getProjects().get(0).getId();
        testInnerProjectData.setParentId(parentProjectId);

        ProjectResponseModel myCreatedInnerProject = projectsApi.createNewProject(testInnerProjectData);

        assertThat(myCreatedInnerProject.getName()).isEqualTo(testInnerProjectData.getName());
        assertThat(myCreatedInnerProject.getColor()).isEqualTo(testInnerProjectData.getColor());
        assertThat(myCreatedInnerProject.getViewStyle()).isEqualTo(testInnerProjectData.getViewStyle());
    }

    @Test
    @DisplayName("[API] Обновить проект по ID.")
    void updateProjectTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();

        ProjectResponseModel myCreatedProject = projectsApi.updateProject(projectId, updatedTestProjectData);

        assertThat(myCreatedProject.getName()).isEqualTo(updatedTestProjectData.getName());
        assertThat(myCreatedProject.getColor()).isEqualTo(updatedTestProjectData.getColor());
        assertThat(myCreatedProject.getViewStyle()).isEqualTo(updatedTestProjectData.getViewStyle());
        assertThat(myCreatedProject.isFavorite()).isEqualTo(updatedTestProjectData.isFavorite());
    }

    @Test
    @DisplayName("[API] Удалить проект по ID.")
    void deleteProjectTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String projectId = testData.getProjects().get(0).getId();

        projectsApi.deleteProject(projectId);
        ProjectResponseModel myDeletedProject = projectsApi.getProject(projectId);

        assertThat(myDeletedProject.getName()).isEqualTo("");
        assertThat(myDeletedProject.getOrder()).isEqualTo("0");
    }

    @Test
    @DisplayName("[API] Удалить все проекты пользователя.")
    void deleteAllProjectsTest() {

        int templateNumber = 1;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        data.create(templateNumber, whatIsCreate);

        projectsApi.deleteProjects();

        int currentProjectCount = projectsApi.getAllProjects().size();
        assertThat(currentProjectCount).isEqualTo(1);
    }
}