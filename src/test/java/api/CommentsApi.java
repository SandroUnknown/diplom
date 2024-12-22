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

    /*
    @Step("[API] Создать новую метку.")
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
    
    public CommentResponseModel createNewComment(String commentName) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .name(commentName)
                .build();

        return createNewComment(commentData);
    }

    @Step("[API] Обновить метку.")
    public CommentResponseModel updateComment(String commentId, CommentRequestModel commentData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(commentData)
                .when()
                .post(ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }

    public CommentResponseModel updateComment(String commentId, String commentName) {

        CommentRequestModel commentData = CommentRequestModel.builder()
                .name(commentName)
                .build();

        return updateComment(commentId, commentData);
    }

    @Step("[API] Получить метку.")
    public CommentResponseModel getComment(String commentId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT + commentId)
                .then()
                .spec(responseSpec200)
                .extract().as(CommentResponseModel.class);
    }

    @Step("[API] Получить все метки пользователя.")
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
    }

    @Step("[API] Удалить метку.")
    public void deleteComment(String commentId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(ENDPOINT + commentId)
                .then()
                .spec(responseSpec204);
    }*/
}
