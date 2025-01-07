package tests.api;

import data.DataCreator;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;
import models.data.TestDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 28 сек / 7 тестов

public class CommentsApiTest extends ApiTestBase {

    private final CommentRequestModel testCommentData = CommentRequestModel.builder()
            .content("НОВЫЙ КОММЕНТАРИЙ")
            .build();

    private final CommentRequestModel updatedTestCommentData = CommentRequestModel.builder()
            .content("ОБНОВЛЁННЫЙ КОММЕНТАРИЙ")
            .build();

    @Test
    @DisplayName("[API] Создать новый комментарий в проекте.")
    void createNewCommentInProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();

        CommentResponseModel myCreatedComment =
                commentsApi.createNewCommentInProject(projectId, testCommentData.getContent());

        assertThat(myCreatedComment.getContent()).isEqualTo(testCommentData.getContent());
    }

    @Test
    @DisplayName("[API] Создать новый комментарий в задаче.")
    void createNewCommentInTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();

        CommentResponseModel myCreatedComment =
                commentsApi.createNewCommentInTask(taskId, testCommentData.getContent());

        assertThat(myCreatedComment.getContent()).isEqualTo(testCommentData.getContent());
    }

    @Test
    @DisplayName("[API] Обновить комментарий по ID.")
    void updateCommentTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        CommentResponseModel myUpdatedComment =
                commentsApi.updateComment(commentId, updatedTestCommentData.getContent());

        assertThat(myUpdatedComment.getContent()).isEqualTo(updatedTestCommentData.getContent());
    }

    @Test
    @DisplayName("[API] Получить комментарий по ID.")
    void getCommentTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        CommentResponseModel myCreatedComment = testData.getCommentsInTasksInSections().get(0);
        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        CommentResponseModel myReceivedComment = commentsApi.getComment(commentId);

        assertThat(myReceivedComment.getContent()).isEqualTo(myCreatedComment.getContent());
    }

    @Test
    @DisplayName("[API] Получить все комментарии в проекте.")
    void getAllCommentsInProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createCommentsInProjects(true)
                .create();

        List<CommentResponseModel> myCreatedComments = testData.getCommentsInProjects();
        String projectId = testData.getProjects().get(0).getId();

        List<CommentResponseModel> myReceivedComments = commentsApi.getAllCommentsInProject(projectId);

        assertThat(myReceivedComments.size()).isEqualTo(myCreatedComments.size());
        for(int i = 0; i < myCreatedComments.size(); i++) {
            assertThat(myReceivedComments.get(i).getContent()).isEqualTo(myCreatedComments.get(i).getContent());
        }
    }

    @Test
    @DisplayName("[API] Получить все комментарии в задаче.")
    void getAllCommentsInTaskTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();

        List<CommentResponseModel> myCreatedComments = new ArrayList<>();
        for(CommentResponseModel myCreatedComment : testData.getCommentsInTasksInSections()) {
            if (myCreatedComment.getTaskId().equals(taskId)) {
                myCreatedComments.add(myCreatedComment);
            }
        }

        List<CommentResponseModel> myReceivedComments = commentsApi.getAllCommentsInTask(taskId);

        assertThat(myReceivedComments.size()).isEqualTo(myCreatedComments.size());
        for(int i = 0; i < myCreatedComments.size(); i++) {
            assertThat(myReceivedComments.get(i).getContent()).isEqualTo(myCreatedComments.get(i).getContent());
        }
    }

    @Test
    @DisplayName("[API] Удалить комментарий по ID.")
    void deleteCommentTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        String commentId = testData.getCommentsInTasksInSections().get(0).getId();
        String taskId = testData.getCommentsInTasksInSections().get(0).getTaskId();

        List<CommentResponseModel> myCreatedComments = new ArrayList<>();
        for(CommentResponseModel myCreatedComment : testData.getCommentsInTasksInSections()) {
            if (myCreatedComment.getTaskId().equals(taskId)) {
                myCreatedComments.add(myCreatedComment);
            }
        }
        int createdCommentCount = myCreatedComments.size();

        commentsApi.deleteComment(commentId);

        int currentCommentCount = commentsApi.getAllCommentsInTask(taskId).size();
        assertThat(currentCommentCount).isEqualTo(createdCommentCount - 1);
    }
}