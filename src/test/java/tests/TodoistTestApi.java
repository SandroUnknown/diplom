package tests;

import api.ProjectsApi;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;

public class TodoistTestApi extends TestBase {

    @DisplayName("Создать новый проект (API)")
    @Test
    void createNewProjectTest() {

        ProjectRequestModel projectData = new ProjectRequestModel();
        projectData.setName("New Project Test 6");
        projectData.setColor("yellow");

        ProjectsApi newApi = new ProjectsApi();
        newApi.createNewProject(projectData);
    }

    @DisplayName("Удалить один проект по ID (API)")
    @Test
    void deleteProjectTest() {

        ProjectsApi newApi = new ProjectsApi();
        // создать новый проект и сразу получить его АйДи
        String projectId = newApi.createNewProject("New Project Test 7").getId();

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом удаляется...
        sleep(2000);

        // получить весь проект по АйДи (избыточно?)
        ProjectResponseModel project = newApi.getProject(projectId);

        // удалить проект по АйДи
        newApi.deleteProject(project.getId());
    }

    @DisplayName("Удалить все проекты (API)")
    @Test
    void deleteAllProjectsTest() {

        ProjectsApi newApi = new ProjectsApi();

        // Создаем предварительно проекты
        newApi.createNewProject("New Project Test 11");
        newApi.createNewProject("New Project Test 12");
        newApi.createNewProject("New Project Test 13");

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом удаляется...
        sleep(2000);

        // Удалить все проекты
        newApi.deleteAllProjects();
    }

    @DisplayName("Обновить проект по ID (API)")
    @Test
    void updateProjectTest() {

        ProjectsApi newApi = new ProjectsApi();

        // Создаем предварительно проект (и сразу получаем его ID)
        String projectId = newApi.createNewProject("New Project Test 15").getId();

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом обновится...
        sleep(2000);

        // Обновить проект (изменить имя на - New Project Test 15 UPDATED)
        ProjectRequestModel newProjectData = new ProjectRequestModel();
        newProjectData.setName("New Project Test 15 UPDATED");
        //newProjectData.setColor("red");
        newProjectData.setFavorite(true);
        //newProjectData.setViewStyle(null);

        newApi.updateProject(projectId, newProjectData);

    }

}
