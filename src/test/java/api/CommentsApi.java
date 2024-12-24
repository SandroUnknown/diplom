package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

// TODO : Добавить аттачи в создание коммента?
// TODO : Вероятно изменить перегрузки Создания и Получения (привести их к единому формату)
public class CommentsApi extends BaseApi {

    @Step("[API] Создать новый комментарий.")
    public CommentResponseModel createNewComment(CommentRequestModel commentData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(commentData)
                .when()
                .post(COMMENTS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }

    public CommentResponseModel createNewCommentInProject(String projectId, String commentContent) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .projectId(projectId)
                .build();

        return createNewComment(commentData);
    }

    public CommentResponseModel createNewCommentInTask(String taskId, String commentContent) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .taskId(taskId)
                .build();

        return createNewComment(commentData);
    }

    @Step("[API] Обновить комментарий.")
    public CommentResponseModel updateComment(String commentId, String commentContent) {

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
                .extract().as(CommentResponseModel.class);
    }

    @Step("[API] Получить комментарий.")
    public CommentResponseModel getComment(String commentId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(COMMENTS_ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }

    @Step("[API] Получить все комментарии в проекте.")
    public List<CommentResponseModel> getAllCommentsInProject(String projectId) {

        RequestSpecification request = given()
                .queryParam("project_id", projectId);

        return getAllComments(request);
    }

    @Step("[API] Получить все комментарии в задаче.")
    public List<CommentResponseModel> getAllCommentsInTask(String taskId) {

        RequestSpecification request = given()
                .queryParam("task_id", taskId);

        return getAllComments(request);
    }

    private List<CommentResponseModel> getAllComments(RequestSpecification request) {

        return request
                .spec(requestGetSpec)
                .when()
                .get(COMMENTS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", CommentResponseModel.class);
    }

    @Step("[API] Удалить комментарий.")
    public void deleteComment(String commentId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(COMMENTS_ENDPOINT + commentId)
                .then()
                .spec(responseSpec204);
    }
}
