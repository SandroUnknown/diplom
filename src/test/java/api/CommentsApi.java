package api;

import enums.CommentField;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.comments.CommentRequestModel;
import models.comments.LabelResponseModel;

import java.util.Arrays;
import java.util.List;

import static enums.CommentField.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specification.*;

// TODO : Вероятно изменить перегрузки Создания и Получения (привести их к единому формату)
public class CommentsApi extends BaseApi {

    @Step("Создать новый комментарий")
    public LabelResponseModel createNewComment(CommentRequestModel commentData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(commentData)
                .when()
                .post(COMMENTS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    public LabelResponseModel createNewCommentInProject(String projectId, String commentContent) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .projectId(projectId)
                .build();

        return createNewComment(commentData);
    }

    public LabelResponseModel createNewCommentInProject(CommentRequestModel commentData) {

        return createNewComment(commentData);
    }

    public LabelResponseModel createNewCommentInTask(String taskId, String commentContent) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .taskId(taskId)
                .build();

        return createNewComment(commentData);
    }

    @Step("Обновить комментарий")
    public LabelResponseModel updateComment(String commentId, String commentContent) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .build();

        return given()
                .spec(requestPostWithIdSpec)
                .body(commentData)
                .when()
                .post(COMMENTS_ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    @Step("Получить комментарий")
    public LabelResponseModel getComment(String commentId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(COMMENTS_ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    @Step("Получить все комментарии в проекте")
    public List<LabelResponseModel> getAllCommentsInProject(String projectId) {

        RequestSpecification request = given()
                .queryParam("project_id", projectId);

        return getAllComments(request);
    }

    @Step("Получить все комментарии в задаче")
    public List<LabelResponseModel> getAllCommentsInTask(String taskId) {

        RequestSpecification request = given()
                .queryParam("task_id", taskId);

        return getAllComments(request);
    }

    private List<LabelResponseModel> getAllComments(RequestSpecification request) {

        return request
                .spec(requestGetSpec)
                .when()
                .get(COMMENTS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", LabelResponseModel.class);
    }

    @Step("Удалить комментарий")
    public void deleteComment(String commentId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(COMMENTS_ENDPOINT + commentId)
                .then()
                .spec(responseSpec204);
    }

    @Step("Проверить комментарий")
    public void checkComment(String commentId, CommentRequestModel expectedComment, CommentField... checkFields) {

        List<CommentField> fieldsList = Arrays.asList(checkFields);
        LabelResponseModel actualComment = getComment(commentId);

        if (fieldsList.contains(CONTENT)) {
            checkContent(actualComment.getContent(), expectedComment.getContent());
        }

        if (fieldsList.contains(PROJECT_ID)) {
            checkProjectId(actualComment.getProjectId(), expectedComment.getProjectId());
        }

        if (fieldsList.contains(TASK_ID)) {
            checkTaskId(actualComment.getTaskId(), expectedComment.getTaskId());
        }
    }

    @Step("Проверить содержимое полученного комментария")
    private void checkContent(String actualContent, String expectedContent) {
        assertThat(actualContent).isEqualTo(expectedContent);
    }

    @Step("Проверить, что комментарий создан в правильном проекте")
    private void checkProjectId(String actualContent, String expectedContent) {
        assertThat(actualContent).isEqualTo(expectedContent);
    }

    @Step("Проверить, что комментарий создан в правильной задаче")
    private void checkTaskId(String actualContent, String expectedContent) {
        assertThat(actualContent).isEqualTo(expectedContent);
    }
}