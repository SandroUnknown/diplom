package tests;

import api.ProjectsApi;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static enums.Color.*;
import static enums.ViewStyle.*;

public class TodoistTestApi extends TestBase {

    // TODO : Передавать имя проекта (и др данные) из вне?
    @Test
    @DisplayName("Создать новый проект (API)")
    void createNewProjectTest() {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("New Project Test 6")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();

        ProjectsApi newApi = new ProjectsApi();
        newApi.createNewProject(projectData);
    }

    @Test
    @DisplayName("Удалить один проект по ID (API)")
    void deleteProjectTest() {

        // создать новый проект и сразу получить его АйДи
        ProjectsApi newApi = new ProjectsApi();
        String projectId = newApi.createNewProject("New Project Test 7").getId();


        // TODO : временный слип, чтобы увидеть, что проект создался, и потом удаляется...
        sleep(2000);

        // получить весь проект по АйДи (избыточно?)
        ProjectResponseModel project = newApi.getProject(projectId);

        // удалить проект по АйДи
        newApi.deleteProject(project.getId());
    }

    @Test
    @DisplayName("Удалить все проекты (API)")
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

    @Test
    @DisplayName("Обновить проект по ID (API)")
    void updateProjectTest() {

        ProjectsApi newApi = new ProjectsApi();

        // Создаем предварительно проект (и сразу получаем его ID)
        String projectId = newApi.createNewProject("New Project Test 15").getId();

        // TODO : временный слип, чтобы увидеть, что проект создался, и потом обновится...
        sleep(2000);

        // Обновить проект (изменить имя на - New Project Test 15 UPDATED)
        ProjectRequestModel newProjectData = ProjectRequestModel.builder()
                .name("New Project Test 15 UPDATED")
                .color(RED)
                .isFavorite(true)
                .viewStyle(BOARD)
                .build();

        newApi.updateProject(projectId, newProjectData);
    }

    // TODO : deleted
    @Test
    void enumTest() {

        //Color color = new Color(RED);

        //Color title = Color.getColorByTitle("red");
        //Color title = Color.getColorById(35);
        //Color title = Color.getColorByHex("#e05194");

        //var a = RED.getColor();

        //System.out.println("123");

    }




}
