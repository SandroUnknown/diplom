package api;

import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class ProjectsApi extends BaseApi {

    @Step("[API] Создать новый проект.")
    public ProjectResponseModel createNewProject(ProjectRequestModel projectData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(projectData)
                .when()
                .post(PROJECTS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(ProjectResponseModel.class);
    }

    public ProjectResponseModel createNewProject(String parentProjectId, String projectName) {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .parentId(parentProjectId)
                .name(projectName)
                .build();

        return createNewProject(projectData);
    }

    public ProjectResponseModel createNewProject(String projectName) {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name(projectName)
                .build();

        return createNewProject(projectData);
    }

    @Step("[API] Обновить проект.")
    public ProjectResponseModel updateProject(String projectId, ProjectRequestModel newProjectData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(newProjectData)
                .when()
                .post(PROJECTS_ENDPOINT + projectId)
                .then()
                .spec(responseSpec200)
                .extract().as(ProjectResponseModel.class);
    }

    public ProjectResponseModel updateProject(String projectId, String projectName) {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name(projectName)
                .build();

        return updateProject(projectId, projectData);
    }

    @Step("[API] Получить проект.")
    public ProjectResponseModel getProject(String projectId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(PROJECTS_ENDPOINT + projectId)
                .then()
                .spec(responseSpec200)
                .extract().as(ProjectResponseModel.class);
    }

    @Step("[API] Получить все проекты.")
    public List<ProjectResponseModel> getAllProjects() {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(PROJECTS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", ProjectResponseModel.class);
    }

    @Step("[API] Удалить проект.")
    public void deleteProject(String projectId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(PROJECTS_ENDPOINT + projectId)
                .then()
                .spec(responseSpec204);
    }

    @Step("[API] Удалить список проектов.")
    public void deleteProjects(List<String> projectsId) {

        for (String projectId : projectsId) {
            deleteProject(projectId);
        }
    }

    @Step("[API] Удалить все проекты.")
    public void deleteProjects() {

        List<ProjectResponseModel> projects = getAllProjects();

        for (ProjectResponseModel project : projects) {
            String projectId = project.getId();
            deleteProject(projectId);
        }
    }
}