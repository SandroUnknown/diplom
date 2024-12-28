package tests.api;

import enums.Color;
import models.data.TestData;
import models.data.TestDataConfig;
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

        LabelRequestModel labelData = LabelRequestModel.builder().name("МЕТКА (с параметрами)").color(Color.BLUE).order(2).isFavorite(true).build();

        labelsApi.createNewLabel(labelData);

        // TODO : сделать проверку
    }

    @Test
    @DisplayName("[API] Обновить метку по ID.")
    void updateLabelTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID метки
        String labelId = testData.getLabels().get(0).getId();

        // Обновляем метку!
        LabelResponseModel label = labelsApi.updateLabel(labelId, "ОБНОВЛЁННАЯ МЕТКА");

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить метку по ID.")
    void getLabelTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID метки
        String labelId = testData.getLabels().get(0).getId();

        // Обновляем метку!
        LabelResponseModel label = labelsApi.getLabel(labelId);

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Получить все метки пользователя.")
    void getAllLabelsTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        // Создаем!
        TestData testData = data.create(1, whatIsCreate);

        // Получаем все метки
        List<LabelResponseModel> labels = labelsApi.getAllLabels();

        // TODO : сделать проверку
        System.out.println();
    }

    @Test
    @DisplayName("[API] Удалить метку по ID.")
    void deleteLabelTest() {

        // Что создаем?
        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        // Создаем!
        TestData testData = data.create(0, whatIsCreate);

        // Получаем ID метки
        String labelId = testData.getLabels().get(0).getId();

        // Удаляем метку!
        labelsApi.deleteLabel(labelId);

        // TODO : сделать проверку
        System.out.println();
    }

    // TODO : Создание метки с уже имеющимся именем? Код ответа 400
}