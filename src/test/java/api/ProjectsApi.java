package api;

import io.qameta.allure.Step;
import models.projects.ProjectResponseModel;
import models.projects.ProjectRequestModel;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class ProjectsApi {

    // === СОЗДАТЬ (один) =====================================================================================

    @Step("Создать новый проект (API) - POST")
    public ProjectResponseModel createNewProject(ProjectRequestModel projectData) {

        // TODO : Что такое parent id?
        // TODO : Сделать viewStyle через ENUM
        // TODO : Сделать color через ENUM

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



    // === ПОЛУЧИТЬ (один или все) =====================================================================================

    @Step("Получить проект по ID (API) - GET")
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

    @Step("Получить все проекты (API) - GET")
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



    // === ОБНОВИТЬ (один) =====================================================================================

    @Step("Обновить проект по ID (API) - POST")
    public ProjectResponseModel updateProject(String projectId, ProjectRequestModel newProjectData) {

        // TODO : Сделать viewStyle через ENUM
        // TODO : Сделать color через ENUM

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



    // === УДАЛИТЬ (один или все) =====================================================================================

    @Step("Удалить проект по ID (API) - DELETE")
    public void deleteProject(String projectId) {

        // TODO : использую общую спецификацию requestGetSpec для метода Delete? - исправить?

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