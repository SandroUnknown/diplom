package tests.api;

import helpers.annotations.CleanupTestData;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static enums.Color.*;
import static enums.ViewStyle.*;

public class ProjectsApiTest extends TestBase {

    @Test
    @CleanupTestData
    @DisplayName("Создать новый проект (API)")
    void createNewProjectTest() {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project Test 27")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();

        projectsApi.createNewProject(projectData);
    }

    @Test
    @DisplayName("Удалить один проект по ID (API)")
    void deleteProjectTest() {

        // создать новый проект и сразу получить его АйДи
        String projectId = projectsApi.createNewProject("New Project Test 7").getId();

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом удаляется...
        sleep(2000);

        // получить весь проект по АйДи (избыточно?)
        ProjectResponseModel project = projectsApi.getProject(projectId);

        // удалить проект по АйДи
        projectsApi.deleteProject(project.getId());
    }

    @Test
    @DisplayName("Удалить все проекты (API)")
    void deleteAllProjectsTest() {

        // Создаем предварительно проекты
        projectsApi.createNewProject("New Project Test 11");
        projectsApi.createNewProject("New Project Test 12");
        projectsApi.createNewProject("New Project Test 13");

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом удаляется...
        sleep(2000);

        // Удалить все проекты
        projectsApi.deleteProjects();
    }

    @Test
    @DisplayName("Обновить проект по ID (API)")
    void updateProjectTest() {

        // Создаем предварительно проект (и сразу получаем его ID)
        String projectId = projectsApi.createNewProject("New Project Test 15").getId();

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом обновится...
        sleep(2000);

        // Обновить проект (изменить имя на - New Project Test 15 UPDATED)
        ProjectRequestModel newProjectData = ProjectRequestModel.builder()
                .name("New Project Test 15 UPDATED")
                .color(RED)
                .isFavorite(true)
                .viewStyle(BOARD)
                .build();

        projectsApi.updateProject(projectId, newProjectData);
    }

    // ==========================

    @Test
    @DisplayName("Создать новый проект (API)")
    void createNewProjectInProjectTest() {

        ProjectRequestModel parentProjectData = ProjectRequestModel.builder()
                .name("PARENT Project Test 20")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();

        // Создать родительский проект
        String parentProjectId = projectsApi.createNewProject(parentProjectData).getId();

        // Создать дочерний проект
        projectsApi.createNewProject(parentProjectId,"CHILD Project Test 20");
    }

}