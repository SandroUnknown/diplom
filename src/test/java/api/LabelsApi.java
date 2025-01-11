package api;

import io.qameta.allure.Step;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

// TODO : узнать можно ли одну модель использовать и для реквест, и для респонс
// TODO : вероятно добавить методы для ОБЩИХ меток? (аразобраться как их использовать) [опционально]
public class LabelsApi extends BaseApi {

    @Step("Создать новую персональную метку")
    public LabelResponseModel createNewLabel(LabelRequestModel labelData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(labelData)
                .when()
                .post(LABELS_ENDPOINT)
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

    @Step("Обновить персональную метку")
    public LabelResponseModel updateLabel(String labelId, LabelRequestModel labelData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(labelData)
                .when()
                .post(LABELS_ENDPOINT + labelId)
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

    @Step("Получить персональную метку")
    public LabelResponseModel getLabel(String labelId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(LABELS_ENDPOINT + labelId)
                .then()
                .spec(responseSpec200)
                .extract().as(LabelResponseModel.class);
    }

    @Step("Получить все персональные метки пользователя")
    public List<LabelResponseModel> getAllLabels() {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(LABELS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", LabelResponseModel.class);
    }

    @Step("Удалить персональную метку")
    public void deleteLabel(String labelId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(LABELS_ENDPOINT + labelId)
                .then()
                .spec(responseSpec204);
    }

    @Step("Удалить все персональные метки")
    public void deleteLabels() {

        List<LabelResponseModel> labels = getAllLabels();

        for (LabelResponseModel label : labels) {
            String labelId = label.getId();
            deleteLabel(labelId);
        }
    }
}