package api;

import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class ProjectsApi {

    @Step("Создать новый проект (API)")
    public ProjectResponseModel createNewProject(ProjectRequestModel projectData) {
        // TODO : Что такое parent id?

        return
                given()
                        .spec(requestPostWithIdSpec)
                        .body(projectData)
                        .when()
                        .post("/projects")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(ProjectResponseModel.class);
    }

    public ProjectResponseModel createNewProject(String projectName) {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name(projectName)
                .build();
        return createNewProject(projectData);
    }

    @Step("Обновить проект по ID (API)")
    public ProjectResponseModel updateProject(String projectId, ProjectRequestModel newProjectData) {

        return
                given()
                        .spec(requestPostWithIdSpec)
                        .body(newProjectData)
                        .when()
                        .post("/projects/" + projectId)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(ProjectResponseModel.class);
    }

    @Step("Получить проект по ID (API)")
    public ProjectResponseModel getProject(String projectId) {

        return
                given()
                        .spec(requestGetSpec)
                        .when()
                        .get("/projects/" + projectId)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(ProjectResponseModel.class);
    }

    @Step("Получить все проекты (API)")
    public List<ProjectResponseModel> getAllProjects() {

        return
                given()
                        .spec(requestGetSpec)
                        .when()
                        .get("/projects")
                        .then()
                        .spec(responseSpec200)
                        .extract()
                        .jsonPath()
                        .getList(".", ProjectResponseModel.class);
    }

    @Step("Удалить проект по ID (API)")
    public void deleteProject(String projectId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete("/projects/" + projectId)
                .then()
                .spec(responseSpec204);
    }

    @Step("Удалить все проекты (API)")
    public void deleteAllProjects() {

        List<ProjectResponseModel> projects = getAllProjects();

        for (ProjectResponseModel project : projects) {
            String projectId = project.getId();
            deleteProject(projectId);
        }
    }
}