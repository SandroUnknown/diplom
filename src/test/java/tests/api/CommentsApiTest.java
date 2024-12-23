package tests.api;

import enums.Color;
import models.comments.CommentResponseModel;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import models.tasks.TaskRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;

public class CommentsApiTest extends TestBase {

    @Test
    @DisplayName("Создать новый коммент в проекте (API)")
    void createNewCommentInProjectTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 1 for test COMMENT").getId();

        // Создаем комментарий
        commentsApi.createNewComment(projectId, null, "New comment, bla-bla-bla...");
    }

    @Test
    @DisplayName("Создать новый коммент в задаче (API)")
    void createNewCommentInTaskTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 2 for test COMMENT").getId();

        // Создаем задачу
        TaskRequestModel taskData = TaskRequestModel.builder()
                .projectId(projectId)
                .content("New task for test COMMENT")
                .build();
        String taskId = tasksApi.createNewTask(taskData).getId();

        // Создаем комментарий
        commentsApi.createNewComment(null, taskId, "New comment, bla-bla-bla...");
    }

    @Test
    @DisplayName("Получить коммент по ID (API)")
    void getCommentTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 3 for test COMMENT").getId();

        // Создаем задачу
        TaskRequestModel taskData = TaskRequestModel.builder()
                .projectId(projectId)
                .content("New task 3 for test COMMENT")
                .build();
        String taskId = tasksApi.createNewTask(taskData).getId();

        // Создаем комментарий
        String commentId = commentsApi.createNewComment(null, taskId, "New comment 3, bla-bla-bla...").getId();

        // Получаем коммент по айди
        CommentResponseModel comment = commentsApi.getComment(commentId);

        System.out.println();
    }

    @Test
    @DisplayName("Получить все комменты в проекте (API)")
    void getAllCommentsInProjectTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 10 for test COMMENT").getId();

        // Создаем задачу
        TaskRequestModel taskData = TaskRequestModel.builder()
                .projectId(projectId)
                .content("New task for test COMMENT")
                .build();
        String taskId = tasksApi.createNewTask(taskData).getId();

        // Создаем комментарий
        commentsApi.createNewComment(null, taskId, "New comment 1, bla-bla-bla...");
        commentsApi.createNewComment(null, taskId, "New comment 2, bla-bla-bla...");
        commentsApi.createNewComment(null, taskId, "New comment 3, bla-bla-bla...");

        // Получаем все комменты в проекте
        List<CommentResponseModel> comments = commentsApi.getAllCommentsInTask(taskId);

        System.out.println();
    }

    @Test
    @DisplayName("Получить все комменты в задаче (API)")
    void getAllCommentsInTaskTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 11 for test COMMENT").getId();

        // Создаем комментарии
        commentsApi.createNewComment(projectId, null, "New comment 1, bla-bla-bla...");
        commentsApi.createNewComment(projectId, null, "New comment 2, bla-bla-bla...");
        commentsApi.createNewComment(projectId, null, "New comment 3, bla-bla-bla...");

        // Получаем все комменты в проекте
        List<CommentResponseModel> comments = commentsApi.getAllCommentsInProject(projectId);

        System.out.println();
    }

    @Test
    @DisplayName("Обновить коммент по ID (API)")
    void updateCommentTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 4 for test COMMENT").getId();

        // Создаем задачу
        TaskRequestModel taskData = TaskRequestModel.builder()
                .projectId(projectId)
                .content("New task 4 for test COMMENT")
                .build();
        String taskId = tasksApi.createNewTask(taskData).getId();

        // Создаем комментарий
        String commentId = commentsApi.createNewComment(null, taskId, "New comment 3, bla-bla-bla...").getId();

        // TODO : удалить при релизе
        sleep(3000);

        // Обновляем коммент по айди
        commentsApi.updateComment(commentId, "(updated) New comment 3, bla-bla-bla...");

        System.out.println();
    }

    @Test
    @DisplayName("Удалить коммент по ID (API)")
    void deleteCommentTest() {

        // Создаем новый проект
        String projectId = projectsApi.createNewProject("PROJECT 5 for test COMMENT").getId();

        // Создаем задачу
        TaskRequestModel taskData = TaskRequestModel.builder()
                .projectId(projectId)
                .content("New task 5 for test COMMENT")
                .build();
        String taskId = tasksApi.createNewTask(taskData).getId();

        // Создаем комментарий
        String commentId = commentsApi.createNewComment(null, taskId, "New comment 5, bla-bla-bla...").getId();

        // TODO : удалить при релизе
        sleep(3000);

        // Удаляем коммент по айди
        commentsApi.deleteComment(commentId);

        System.out.println();
    }
}