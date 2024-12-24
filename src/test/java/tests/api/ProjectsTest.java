package tests.api;

import helpers.annotations.CleanupTestData;
import models.projects.ProjectRequestModel;
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
    @DisplayName("[API] Создать новый вложенный проект.")
    void createNewProjectInProjectTest() {

        String parentProjectId = data.createProject();

        projectsApi.createNewProject(parentProjectId,"ДОЧЕРНИЙ ПРОЕКТ");

        // TODO : выполнить проверку
    }

    @Test
    @DisplayName("[API] Обновить проект по ID.)")
    void updateProjectTest() {

        String projectId = data.createProject();

        //sleep(3000); // TODO : убрать на релизе

        ProjectRequestModel newProjectData = ProjectRequestModel.builder()
                .name("ОБНОВЛЁННЫЙ ПРОЕКТ")
                .color(RED)
                .isFavorite(true)
                .viewStyle(BOARD)
                .build();

        projectsApi.updateProject(projectId, newProjectData);

        // TODO : выполнить проверку
    }

    @Test
    @DisplayName("[API] Удалить проект по ID.")
    void deleteProjectTest() {

        String projectId = data.createProject();

        //sleep(3000); // TODO : убрать на релизе

        projectsApi.deleteProject(projectId);

        // TODO : выполнить проверку
    }

    @Test
    @DisplayName("[API] Удалить все проекты пользователя.")
    void deleteAllProjectsTest() {

        data.createProjects(2, 4);

        //sleep(3000); // TODO : убрать на релизе

        projectsApi.deleteProjects();

        // TODO : выполнить проверку
    }
}