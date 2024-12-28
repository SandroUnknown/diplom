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

    @Test
    @DisplayName("[API] Создать новый проект.")
    void createNewProjectTest() {

        ProjectRequestModel projectData = ProjectRequestModel.builder()
                .name("Тестовый проект")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();

        projectsApi.createNewProject(projectData);

        // TODO : выполнить проверку
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

        // Создаем новые данные для проекта
        ProjectRequestModel newProjectData = ProjectRequestModel.builder()
                .name("ОБНОВЛЁННЫЙ ПРОЕКТ")
                .color(RED)
                .isFavorite(true)
                .viewStyle(LIST)
                .build();

        // Обновляем проект!
        ProjectResponseModel project = projectsApi.updateProject(projectId, newProjectData);

        // TODO : сделать проверку
        System.out.println();
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

        // Удаляем проект!
        projectsApi.deleteProject(projectId);

        // TODO : сделать проверку
        System.out.println();
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

        // Удаляем проект!
        projectsApi.deleteProjects();

        // TODO : сделать проверку
        System.out.println();

    }

}































