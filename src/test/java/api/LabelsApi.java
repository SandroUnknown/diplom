package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

// TODO : заменить все метки на ЛИЧНЫЕ метки + узнать можно ли одну модель использовать и для реквест, и для респонс
public class LabelsApi {

    private static final String ENDPOINT = "/labels/";

    @Step("[API] Создать новую метку.")
    public LabelResponseModel createNewLabel(LabelRequestModel labelData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(labelData)
                .when()
                .post(ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }
    
    public LabelResponseModel createNewLabel(String labelName) {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .name(labelName)
                .build();

        return createNewLabel(labelData);
    }

    @Step("[API] Обновить метку.")
    public LabelResponseModel updateLabel(String labelId, LabelRequestModel labelData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(labelData)
                .when()
                .post(ENDPOINT + labelId)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    public LabelResponseModel updateLabel(String labelId, String labelName) {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .name(labelName)
                .build();

        return updateLabel(labelId, labelData);
    }

    @Step("[API] Получить метку.")
    public LabelResponseModel getLabel(String labelId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT + labelId)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    @Step("[API] Получить все метки пользователя.")
    public List<LabelResponseModel> getAllLabels() {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", LabelResponseModel.class);
    }

    @Step("[API] Удалить метку.")
    public void deleteLabel(String labelId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(ENDPOINT + labelId)
                .then()
                .spec(responseSpec204);
    }
}
