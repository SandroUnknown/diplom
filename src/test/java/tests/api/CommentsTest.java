package tests.api;

import helpers.annotations.CleanupTestData;
import models.comments.CommentResponseModel;
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

        String projectId = data.createProject();

        commentsApi.createNewCommentInProject(projectId, "New comment, bla-bla-bla...");

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Создать новый комментарий в задаче.")
    void createNewCommentInTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);

        commentsApi.createNewCommentInTask(taskId, "New comment, bla-bla-bla...");

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Обновить комментарий по ID.")
    void updateCommentTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);
        String commentId = data.createCommentInTask(taskId);

        //sleep(5000); // TODO : убрать при релизе
        commentsApi.updateComment(commentId, "ОБНОВЛЁННЫЙ КОММЕНТАРИЙ");

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить комментарий по ID.")
    void getCommentTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);
        String commentId = data.createCommentInTask(taskId);

        CommentResponseModel comment = commentsApi.getComment(commentId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все комментарии в проекте.")
    void getAllCommentsInProjectTest() {

        String projectId = data.createProject();
        data.createCommentsInProject(projectId, 2);

        List<CommentResponseModel> comments = commentsApi.getAllCommentsInProject(projectId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все комментарии в задаче.")
    void getAllCommentsInTaskTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);
        data.createCommentsInTask(taskId, 2);

        List<CommentResponseModel> comments = commentsApi.getAllCommentsInTask(taskId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Удалить комментарий по ID.")
    void deleteCommentTest() {

        String projectId = data.createProject();
        String sectionId = data.createSection(projectId);
        String taskId = data.createTask(sectionId);
        String commentId = data.createCommentInTask(taskId);

        //sleep(5000); // TODO : убрать при релизе
        commentsApi.deleteComment(commentId);

        // TODO : сделать проверку
        System.out.println();
    }
}