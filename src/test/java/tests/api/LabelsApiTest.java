package tests.api;

import enums.Color;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;

public class LabelsApiTest extends TestBase {

    @Test
    @DisplayName("Создать новую метку (API)")
    void createNewLabelTest() {

        labelsApi.createNewLabel("TEST LABEL");
    }

    @Test
    @DisplayName("Создать новую метку с заполнением всех параметров (API)")
    void createNewLabelWithAllParamsTest() {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .name("TEST LABEL WITH PARAMS")
                .color(Color.BLUE)
                .order(2)
                .isFavorite(true)
                .build();

        labelsApi.createNewLabel(labelData);
    }

    @Test
    @DisplayName("Получить метку по ID (API)")
    void getLabelTest() {

        // Создаем метку и получаем её айди метки.
        String labelId = labelsApi.createNewLabel("TEST LABEL 2").getId();

        // Получаем метку по айди
        LabelResponseModel myLabel = labelsApi.getLabel(labelId);

        System.out.println();
    }

    @Test
    @DisplayName("Получить все активные задачи (API)")
    void getAllLabelsTest() {

        // Получаем все задачи в аккаунте
        List<LabelResponseModel> labels = labelsApi.getAllLabels();

        System.out.println();
    }

    @Test
    @DisplayName("Обновить метку по ID (API)")
    void updateLabelTest() {

        // Создаем метку.
        String labelId = labelsApi.createNewLabel("TEST LABEL 3").getId();

        // Обновляем метку по айди
        LabelResponseModel label = labelsApi.updateLabel(labelId, "(updated) TEST LABEL 3");

        System.out.println();
    }

    @Test
    @DisplayName("Удалить метку по ID (API)")
    void deleteLabelTest() {

        // Создаем метку.
        String labelId = labelsApi.createNewLabel("TEST LABEL 4").getId();

        // TODO : удалить при релизе
        sleep(2000);

        // Удаляем метку по айди
        labelsApi.deleteLabel(labelId);
    }

    // Создание метки с уже имеющимся именем? Код ответа 400
}