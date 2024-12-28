package tests.api;

import models.comments.CommentResponseModel;
import models.data.TestData;
import models.data.TestDataConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 28 сек / 7 тестов

public class CommentsTest extends TestBase {

    @Test
    @DisplayName("[API] Создать новый комментарий в проекте.")
    void createNewCommentInProjectTest() {

        // Что создаем? - проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String projectId = testData.getProjects().get(0).getId();

        // Создаем новый комментарий
        commentsApi.createNewCommentInProject(projectId, "НОВЫЙ КОММЕНТАРИЙ");

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Создать новый комментарий в задаче.")
    void createNewCommentInTaskTest() {

        // Что создаем? - проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Создаем новый комментарий
        commentsApi.createNewCommentInTask(taskId, "НОВЫЙ КОММЕНТАРИЙ");

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Обновить комментарий по ID.")
    void updateCommentTest() {

        // Что создаем? - проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        // Обновляем комментарий
        commentsApi.updateComment(commentId, "ОБНОВЛЁННЫЙ КОММЕНТАРИЙ");

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить комментарий по ID.")
    void getCommentTest() {

        // Что создаем? - проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        // Получить комментарий
        CommentResponseModel comment = commentsApi.getComment(commentId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все комментарии в проекте.")
    void getAllCommentsInProjectTest() {

        // Что создаем? - проект
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createCommentsInProjects(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String projectId = testData.getProjects().get(0).getId();

        // Получить комментарий
        List<CommentResponseModel> comments = commentsApi.getAllCommentsInProject(projectId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все комментарии в задаче.")
    void getAllCommentsInTaskTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // Получаем ID проекта
        String taskId = testData.getTasksInSections().get(0).getId();

        // Получить комментарий
        List<CommentResponseModel> comments = commentsApi.getAllCommentsInTask(taskId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Удалить комментарий по ID.")
    void deleteCommentTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID проекта
        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        // Удаляем комментарий
        commentsApi.deleteComment(commentId);

        // TODO : сделать проверку
        System.out.println();
    }
}