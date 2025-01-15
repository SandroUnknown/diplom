package api;

import enums.Color;
import enums.CheckField;
import io.qameta.allure.Step;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;

import java.util.Arrays;
import java.util.List;

import static enums.CheckField.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specification.*;

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

    @Step("Проверить метку")
    public void checkLabel(String labelId, LabelRequestModel expectedLabel, CheckField... checkFields) {

        LabelResponseModel actualLabel = getLabel(labelId);

        checkEqualsLabel(actualLabel, expectedLabel, checkFields);
    }

    @Step("Проверить соответствие полученной метки")
    public void checkEqualsLabel(LabelResponseModel actualLabel, LabelResponseModel expectedLabel, CheckField... checkFields) {

        List<CheckField> fieldsList = Arrays.asList(checkFields);

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
    public void checkEqualsLabel(LabelResponseModel actualLabel, LabelRequestModel expectedLabel, CheckField... checkFields) {

        List<CheckField> fieldsList = Arrays.asList(checkFields);

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