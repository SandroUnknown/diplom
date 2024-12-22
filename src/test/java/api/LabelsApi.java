package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

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

    /*
    // TODO : доделать
    @Step("[API] Обновить задачу.")
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

    // TODO : доделать
    public LabelResponseModel updateLabel(String labelId, String labelContent) {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .content(labelContent)
                .build();

        return updateLabel(labelId, labelData);
    }

    // TODO : доделать
    @Step("[API] Получить задачу.")
    public LabelResponseModel getLabel(String labelId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT + labelId)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    // TODO : доделать
    @Step("[API] Получить задачи (с фильтром).")
    public List<LabelResponseModel> getLabelsWithFilter(HashMap<String, String> params) {

        List<String> filters = List.of(
                "filter",
                "ids",
                "project_id",
                "section_id",
                "label"
        );

        RequestSpecification request = given();

        for (String filter : filters) {
            String value = params.get(filter);
            if (value != null) {
                request.queryParam(filter, value);
            }
        }

        return request
                .spec(requestGetSpec)
                .when()
                .get(ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", LabelResponseModel.class);
    }

    // TODO : доделать
    @Step("[API] Получить все задачи (во всех проектах).")
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

    // TODO : доделать
    @Step("[API] Закрыть задачу.")
    public void closeLabel(String labelId) {

        given()
                .spec(requestPostSpec)
                .when()
                .post(String.format("%s%s/close", ENDPOINT, labelId))
                .then()
                .spec(responseSpec204);
    }

    // TODO : доделать
    @Step("[API] Открыть ранее закрытую задачу.")
    public void reopenLabel(String labelId) {

        given()
                .spec(requestPostSpec)
                .when()
                .post(String.format("%s%s/reopen", ENDPOINT, labelId))
                .then()
                .spec(responseSpec204);
    }

    // TODO : доделать
    @Step("[API] Удалить задачу.")
    public void deleteLabel(String labelId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(ENDPOINT + labelId)
                .then()
                .spec(responseSpec204);
    }*/
}
