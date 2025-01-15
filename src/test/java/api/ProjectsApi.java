package api;

import enums.CheckField;
import enums.Color;
import enums.ViewStyle;
import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;

import java.util.Arrays;
import java.util.List;

import static enums.CheckField.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specification.*;

public class ProjectsApi extends BaseApi {

    @Step("Создать новый проект")
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

    @Step("Обновить проект")
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

    @Step("Получить проект")
    public ProjectResponseModel getProject(String projectId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(PROJECTS_ENDPOINT + projectId)
                .then()
                .spec(responseSpec200)
                .extract().as(ProjectResponseModel.class);
    }

    @Step("Получить все проекты")
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

    @Step("Удалить проект")
    public void deleteProject(String projectId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(PROJECTS_ENDPOINT + projectId)
                .then()
                .spec(responseSpec204);
    }

    @Step("Удалить все проекты")
    public void deleteProjects() {

        List<ProjectResponseModel> projects = getAllProjects();

        for (ProjectResponseModel project : projects) {
            String projectId = project.getId();
            deleteProject(projectId);
        }
    }

    @Step("Проверить, что проект был корректно создан [API]")
    public void checkProject(ProjectRequestModel expectedProject, CheckField... checkFields) {

        ProjectResponseModel actualProject = getAllProjects().get(1);

        List<CheckField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(NAME)) {
            checkName(actualProject.getName(), expectedProject.getName());
        }

        if (fieldsList.contains(COLOR)) {
            checkColor(actualProject.getColor(), expectedProject.getColor());
        }

        if (fieldsList.contains(FAVORITE)) {
            checkFavorite(actualProject.isFavorite(), expectedProject.isFavorite());
        }

        if (fieldsList.contains(VIEW_STYLE)) {
            checkViewStyle(actualProject.getViewStyle(), expectedProject.getViewStyle());
        }
    }

    @Step("Проверить имя полученного проекта")
    private void checkName(String actualName, String expectedName) {
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Step("Проверить цвет полученного проекта")
    private void checkColor(Color actualColor, Color expectedColor) {
        assertThat(actualColor).isEqualTo(expectedColor);
    }

    @Step("Проверить отметку 'избранное' полученного проекта")
    private void checkFavorite(boolean actualFavorite, boolean expectedFavorite) {
        assertThat(actualFavorite).isEqualTo(expectedFavorite);
    }

    @Step("Проверить вариант отображения полученного проекта")
    private void checkViewStyle(ViewStyle actualViewStyle, ViewStyle ViewStyle) {
        assertThat(actualViewStyle).isEqualTo(ViewStyle);
    }

    @Step("Проверить, что проект успешно удалён [API]")
    public void checkDeleteProject() {
        int projectCount = getAllProjects().size();
        assertThat(projectCount).isEqualTo(1);
    }
}