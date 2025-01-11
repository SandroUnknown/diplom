package api;

import enums.Color;
import enums.LabelField;
import io.qameta.allure.Step;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;

import java.util.Arrays;
import java.util.List;

import static enums.LabelField.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specification.*;

public class LabelsApi extends BaseApi {

    @Step("Создать новую персональную метку")
    public models.labels.LabelResponseModel createNewLabel(LabelRequestModel labelData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(labelData)
                .when()
                .post(LABELS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(models.labels.LabelResponseModel.class);
    }

    public models.labels.LabelResponseModel createNewLabel(String labelName) {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .name(labelName)
                .build();

        return createNewLabel(labelData);
    }

    @Step("Обновить персональную метку")
    public models.labels.LabelResponseModel updateLabel(String labelId, LabelRequestModel labelData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(labelData)
                .when()
                .post(LABELS_ENDPOINT + labelId)
                .then()
                .spec(responseSpec200)
                .extract().as(models.labels.LabelResponseModel.class);
    }

    public models.labels.LabelResponseModel updateLabel(String labelId, String labelName) {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .name(labelName)
                .build();

        return updateLabel(labelId, labelData);
    }

    @Step("Получить персональную метку")
    public models.labels.LabelResponseModel getLabel(String labelId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(LABELS_ENDPOINT + labelId)
                .then()
                .spec(responseSpec200)
                .extract().as(models.labels.LabelResponseModel.class);
    }

    @Step("Получить все персональные метки пользователя")
    public List<models.labels.LabelResponseModel> getAllLabels() {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(LABELS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", models.labels.LabelResponseModel.class);
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

        List<models.labels.LabelResponseModel> labels = getAllLabels();

        for (models.labels.LabelResponseModel label : labels) {
            String labelId = label.getId();
            deleteLabel(labelId);
        }
    }

    @Step("Проверить метку")
    public void checkLabel(String labelId, LabelRequestModel expectedLabel, LabelField... checkFields) {

        LabelResponseModel actualLabel = getLabel(labelId);

        checkEqualsLabel(actualLabel, expectedLabel, checkFields);
    }

    @Step("Проверить соответствие полученной метки")
    public void checkEqualsLabel(LabelResponseModel actualLabel, LabelResponseModel expectedLabel, LabelField... checkFields) {

        List<LabelField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(NAME)) {
            checkName(actualLabel.getName(), expectedLabel.getName());
        }

        if (fieldsList.contains(COLOR)) {
            checkColor(actualLabel.getColor(), expectedLabel.getColor());
        }

        if (fieldsList.contains(ORDER)) {
            checkOrder(actualLabel.getOrder(), expectedLabel.getOrder());
        }

        if (fieldsList.contains(FAVORITE)) {
            checkFavorite(actualLabel.isFavorite(), expectedLabel.isFavorite());
        }
    }

    @Step("Проверить соответствие полученной метки")
    public void checkEqualsLabel(LabelResponseModel actualLabel, LabelRequestModel expectedLabel, LabelField... checkFields) {

        List<LabelField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(NAME)) {
            checkName(actualLabel.getName(), expectedLabel.getName());
        }

        if (fieldsList.contains(COLOR)) {
            checkColor(actualLabel.getColor(), expectedLabel.getColor());
        }

        if (fieldsList.contains(ORDER)) {
            checkOrder(actualLabel.getOrder(), expectedLabel.getOrder());
        }

        if (fieldsList.contains(FAVORITE)) {
            checkFavorite(actualLabel.isFavorite(), expectedLabel.isFavorite());
        }
    }

    @Step("Проверить имя полученной метки")
    private void checkName(String actualName, String expectedName) {
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Step("Проверить цвет полученной метки")
    private void checkColor(Color actualColor, Color expectedColor) {
        assertThat(actualColor).isEqualTo(expectedColor);
    }

    @Step("Проверить порядок полученной метки")
    private void checkOrder(int actualOrder, int expectedOrder) {
        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Step("Проверить отметку 'избранное' полученной метки")
    private void checkFavorite(boolean actualFavorite, boolean expectedFavorite) {
        assertThat(actualFavorite).isEqualTo(expectedFavorite);
    }
}