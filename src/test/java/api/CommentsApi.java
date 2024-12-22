package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class CommentsApi {

    private static final String ENDPOINT = "/comments/";

    @Step("[API] Создать новый комментарий.")
    public CommentResponseModel createNewComment(CommentRequestModel commentData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(commentData)
                .when()
                .post(ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }
    
    public CommentResponseModel createNewComment(String projectId, String taskId, String commentContent) {
        // TODO : доделать: нужно принимать либо проджектАйди, либо таскАйди
        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .build();

        return createNewComment(commentData);
    }

    @Step("[API] Обновить комментарий.")
    public CommentResponseModel updateComment(String commentId, CommentRequestModel commentData) {

    // TODO : под вопросом нужность этого метода? Проверить передать не только имя, но и новый айди проекта, например. Изменится ли место комментария? Если нет - то этот метод не нужен.
        return given()
                .spec(requestPostWithIdSpec)
                .body(commentData)
                .when()
                .post(ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }

    public CommentResponseModel updateComment(String commentId, String commentContent) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .content(commentContent)
                .build();

        return updateComment(commentId, commentData);
    }

    @Step("[API] Получить комментарий.")
    public CommentResponseModel getComment(String commentId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }

    // TODO : ДОДЕЛАТЬ
    /*
    @Step("[API] Получить все комментарии.")
    public List<CommentResponseModel> getAllComments() {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", CommentResponseModel.class);
    }*/

    @Step("[API] Удалить комментарий.")
    public void deleteComment(String commentId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(ENDPOINT + commentId)
                .then()
                .spec(responseSpec204);
    }
}
