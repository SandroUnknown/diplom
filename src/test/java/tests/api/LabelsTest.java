package tests.api;

import enums.Color;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 17 сек / 6 тестов

public class LabelsTest extends TestBase {

    @Test
    @DisplayName("[API] Создать новую метку (с заполнением только имени).")
    void createNewLabelTest() {

        labelsApi.createNewLabel("МЕТКА");

        // TODO : сделать проверку
    }

    // TODO : сделать параметризованным?
    @Test
    @DisplayName("[API] Создать новую метку (с заполнением всех параметров).")
    void createNewLabelWithAllParamsTest() {

        LabelRequestModel labelData = LabelRequestModel.builder()
                .name("МЕТКА (с параметрами)")
                .color(Color.BLUE)
                .order(2)
                .isFavorite(true)
                .build();

        labelsApi.createNewLabel(labelData);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Обновить метку по ID.")
    void updateLabelTest() {

        String labelId = data.createLabel();

        LabelResponseModel label = labelsApi.updateLabel(labelId, "ОБНОВЛЁННАЯ МЕТКА");

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить метку по ID.")
    void getLabelTest() {

        String labelId = data.createLabel();

        LabelResponseModel myLabel = labelsApi.getLabel(labelId);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Получить все метки пользователя.")
    void getAllLabelsTest() {

        data.createLabels(3, 5);

        List<LabelResponseModel> labels = labelsApi.getAllLabels();

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Удалить метку по ID.")
    void deleteLabelTest() {

        String labelId = data.createLabel();

        //sleep(2000); // TODO : удалить при релизе
        labelsApi.deleteLabel(labelId);

        // TODO : сделать проверку
    }

    // TODO : Создание метки с уже имеющимся именем? Код ответа 400
}