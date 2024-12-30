package tests.api;

import models.data.TestData;
import models.data.TestDataConfig;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static enums.Color.*;
import static enums.ViewStyle.*;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 17 сек / 5 тестов

public class ProjectsTest extends TestBase {
        
    private ProjectRequestModel testProjectData = ProjectRequestModel.builder()
                .name("ПРОЕКТ")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();
    private ProjectRequestModel updatedTestProjectData = ProjectRequestModel.builder()
                .name("ОБНОВЛЁННЫЙ ПРОЕКТ")
                .color(RED)
                .isFavorite(true)
                .viewStyle(LIST)
                .build();
    
    @Test
    @DisplayName("[API] Создать новый проект.")
    void createNewProjectTest() {

        projectsApi.createNewProject(testProjectData);

        // TODO : сделать проверку
        ProjectResponseModel myCreatedProject = projectsApi.getAllProjects().get(0);
        assertThat(testProjectData.getName()).isEqualTo(myCreatedProject.getName());
    }

    @Test
    @Disabled
    @DisplayName("[API] Создать новый вложенный проект.")
    void createNewProjectInProjectTest() {

        /*String parentProjectId = data.createProject();

        projectsApi.createNewProject(parentProjectId,"ДОЧЕРНИЙ ПРОЕКТ");*/

        // TODO : выполнить проверку
    }

    @Test
    @DisplayName("[API] Обновить проект по ID.)")
    void updateProjectTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String projectId = testData.getProjects().get(0).getId();
        
        // TODO : проверить, что создался с нужными данными? Нужна ли эта проверка?
        ProjectResponseModel templatesLabel = PROJECT_TEMPLATES.getProjects().get(0);
        ProjectResponseModel myCreatedProject = projectsApi.getProject(projectId);
        assertThat(templatesLabel.getName()).isEqualTo(myCreatedProject.getName());
        assertThat(templatesLabel.getColor()).isEqualTo(myCreatedProject.getColor());
        assertThat(templatesLabel.getViewStyle()).isEqualTo(myCreatedProject.getViewStyle());
        assertThat(templatesLabel.getIsFavorite()).isEqualTo(myCreatedProject.getIsFavorite());

        // Обновляем проект!
        ProjectResponseModel project = projectsApi.updateProject(projectId, updatedTestProjectData);

        // TODO : сделать проверку
        ProjectResponseModel myCreatedProject = projectsApi.getProject(projectId);
        assertThat(updatedTestProjectData.getName()).isEqualTo(myCreatedProject.getName());
        assertThat(updatedTestProjectData.getColor()).isEqualTo(myCreatedProject.getColor());
        assertThat(updatedTestProjectData.getViewStyle()).isEqualTo(myCreatedProject.getViewStyle());
        assertThat(updatedTestProjectData.getIsFavorite()).isEqualTo(myCreatedProject.getIsFavorite());
    }

    @Test
    @DisplayName("[API] Удалить проект по ID.")
    void deleteProjectTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String projectId = testData.getProjects().get(0).getId();

        // TODO : проверить, что проект создался
        //

        // Удаляем проект!
        projectsApi.deleteProject(projectId);

        // TODO : сделать проверку - проверить, что по нужному айди более нет проекта
        /*ProjectResponseModel myCreatedProject = projectsApi.getProject(projectId);
        assertThat(updatedTestProjectData.getName()).isEqualTo(myCreatedProject.getName());
        assertThat(updatedTestProjectData.getColor()).isEqualTo(myCreatedProject.getColor());
        assertThat(updatedTestProjectData.getViewStyle()).isEqualTo(myCreatedProject.getViewStyle());
        assertThat(updatedTestProjectData.getIsFavorite()).isEqualTo(myCreatedProject.getIsFavorite());*/
    }

    @Test
    @DisplayName("[API] Удалить все проекты пользователя.")
    void deleteAllProjectsTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // TODO : проверить, что проекты создались
        //

        // Удаляем проект!
        projectsApi.deleteProjects();

        // TODO : сделать проверку - проверить, что нет ни одного проекта
        /*ProjectResponseModel myCreatedProject = projectsApi.getProject(projectId);
        assertThat(updatedTestProjectData.getName()).isEqualTo(myCreatedProject.getName());
        assertThat(updatedTestProjectData.getColor()).isEqualTo(myCreatedProject.getColor());
        assertThat(updatedTestProjectData.getViewStyle()).isEqualTo(myCreatedProject.getViewStyle());
        assertThat(updatedTestProjectData.getIsFavorite()).isEqualTo(myCreatedProject.getIsFavorite());*/

    }

}































