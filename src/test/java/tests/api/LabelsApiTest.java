package tests.api;

import enums.Color;
import models.data.TestData;
import models.data.TestDataConfig;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные
// Примерное время тестов: 17 сек / 6 тестов

public class LabelsApiTest extends ApiTestBase {

    private LabelRequestModel testLabelData = LabelRequestModel.builder()
            .name("МЕТКА")
            .color(Color.BLUE)
            .order(2)
            .isFavorite(true)
            .build();

    private String newLabelName = "ОБНОВЛЁННАЯ МЕТКА";

    @Test
    @DisplayName("[API] Создать новую метку (с заполнением только имени).")
    void createNewLabelTest() {

        Color defaultColor = Color.CHARCOAL;
        boolean defaultFavorite = false;

        LabelResponseModel myCreatedLabel = labelsApi.createNewLabel(testLabelData.getName());

        assertThat(myCreatedLabel.getName()).isEqualTo(testLabelData.getName());
        assertThat(myCreatedLabel.getColor()).isEqualTo(defaultColor);
        assertThat(myCreatedLabel.isFavorite()).isEqualTo(defaultFavorite);
    }

    // TODO : сделать параметризованным?
    @Test
    @DisplayName("[API] Создать новую метку (с заполнением всех параметров).")
    void createNewLabelWithAllParamsTest() {

        LabelResponseModel myCreatedLabel = labelsApi.createNewLabel(testLabelData);

        assertThat(myCreatedLabel.getName()).isEqualTo(testLabelData.getName());
        assertThat(myCreatedLabel.getColor()).isEqualTo(testLabelData.getColor());
        assertThat(myCreatedLabel.getOrder()).isEqualTo(testLabelData.getOrder());
        assertThat(myCreatedLabel.isFavorite()).isEqualTo(testLabelData.isFavorite());
    }


    @Test
    @DisplayName("[API] Обновить имя метки по ID.")
    void updateLabelTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        String labelId = testData.getLabels().get(0).getId();

        LabelResponseModel myUpdatedLabel = labelsApi.updateLabel(labelId, newLabelName);

        assertThat(myUpdatedLabel.getName()).isEqualTo(newLabelName);
    }


    @Test
    @DisplayName("[API] Получить метку по ID.")
    void getLabelTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        LabelResponseModel myCreatedLabel = testData.getLabels().get(0);
        String labelId = myCreatedLabel.getId();

        LabelResponseModel myReceivedLabel = labelsApi.getLabel(labelId);

        assertThat(myReceivedLabel.getName()).isEqualTo(myCreatedLabel.getName());
        assertThat(myReceivedLabel.getColor()).isEqualTo(myCreatedLabel.getColor());
        assertThat(myReceivedLabel.getOrder()).isEqualTo(myCreatedLabel.getOrder());
        assertThat(myReceivedLabel.isFavorite()).isEqualTo(myCreatedLabel.isFavorite());
    }


    @Test
    @DisplayName("[API] Получить все метки пользователя.")
    void getAllLabelsTest() {

        int templateNumber = 1;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        List<LabelResponseModel> myCreatedLabels = testData.getLabels();

        List<LabelResponseModel> myReceivedLabels = labelsApi.getAllLabels();

        assertThat(myReceivedLabels.size()).isEqualTo(myCreatedLabels.size());
        for(int i = 0; i < myCreatedLabels.size(); i++) {
            assertThat(myReceivedLabels.get(i).getName()).isEqualTo(myCreatedLabels.get(i).getName());
            assertThat(myReceivedLabels.get(i).getColor()).isEqualTo(myCreatedLabels.get(i).getColor());
            assertThat(myReceivedLabels.get(i).getOrder()).isEqualTo(myCreatedLabels.get(i).getOrder());
            assertThat(myReceivedLabels.get(i).isFavorite()).isEqualTo(myCreatedLabels.get(i).isFavorite());
        }
    }


    @Test
    @DisplayName("[API] Удалить метку по ID.")
    void deleteLabelTest() {

        int templateNumber = 0;

        TestDataConfig whatIsCreate = TestDataConfig.builder()
                .createLabels(true)
                .build();

        TestData testData = data.create(templateNumber, whatIsCreate);
        int createdLabelCount = testData.getLabels().size();
        String labelId = testData.getLabels().get(0).getId();

        labelsApi.deleteLabel(labelId);

        int currentLabelCount = labelsApi.getAllLabels().size();
        assertThat(currentLabelCount).isEqualTo(createdLabelCount - 1);
    }

    // TODO : Создание метки с уже имеющимся именем? Код ответа 400
}
