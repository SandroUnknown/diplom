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

    private String newLabelName = "ОБНОВЛЁННАЯ МЕТКА";
    private LabelRequestModel testLabelData = LabelRequestModel.builder()
            .name("МЕТКА")
            .color(Color.BLUE)
            .order(2)
            .isFavorite(true)
            .build();

    @Test
    @DisplayName("[API] Создать новую метку (с заполнением только имени).")
    void createNewLabelTest() {

        labelsApi.createNewLabel(labelName);

        // TODO : проверка
        LabelResponseModel myCreatedLabel = labelsApi.getAllLabels().get(0);
        assertThat(testLabelData.getName()).isEqualTo(myCreatedLabel.getName());
    }

    // TODO : сделать параметризованным?
    @Test
    @DisplayName("[API] Создать новую метку (с заполнением всех параметров).")
    void createNewLabelWithAllParamsTest() {

        LabelRequestModel request = LabelRequestModel.builder()
            .name(labelName)
            .color(Color.BLUE)
            .order(2)
            .isFavorite(true)
            .build();

        labelsApi.createNewLabel(request);

        // TODO : сделать проверку
        LabelResponseModel myCreatedLabel = labelsApi.getAllLabels().get(0);
        assertThat(testLabelData.getName()).isEqualTo(myCreatedLabel.getName());
        assertThat(testLabelData.getColor()).isEqualTo(myCreatedLabel.getColor());
        assertThat(testLabelData.getOrder()).isEqualTo(myCreatedLabel.getOrder());
        assertThat(testLabelData.getFavorite()).isEqualTo(myCreatedLabel.getFavorite());

    }

    @Test
    @DisplayName("[API] Обновить имя метки по ID.")
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
        labelsApi.updateLabel(labelId, newLabelName);

        // TODO : сделать проверку - точно ли ВСЁ нужно проверять? Всё ли я создаю?
        LabelResponseModel templatesLabel = PROJECT_TEMPLATES .getLabels().get(0);
        LabelResponseModel myCreatedLabel = labelsApi.getAllLabels().get(0);
        assertThat(templatesLabel.getName()).isEqualTo(myCreatedLabel.getName());
        assertThat(templatesLabel.getColor()).isEqualTo(myCreatedLabel.getColor());
        assertThat(templatesLabel.getOrder()).isEqualTo(myCreatedLabel.getOrder());
        assertThat(templatesLabel.getFavorite()).isEqualTo(myCreatedLabel.getFavorite());
        
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

        // Получаем метку по ID
        LabelResponseModel myCreatedLabel = labelsApi.getLabel(labelId);

        // TODO : сделать проверку - точно ли ВСЁ нужно проверять? Всё ли я создаю?
        LabelResponseModel templatesLabel = PROJECT_TEMPLATES .getLabels().get(0);
        assertThat(templatesLabel.getName()).isEqualTo(myCreatedLabel.getName());
        assertThat(templatesLabel.getColor()).isEqualTo(myCreatedLabel.getColor());
        assertThat(templatesLabel.getOrder()).isEqualTo(myCreatedLabel.getOrder());
        assertThat(templatesLabel.getFavorite()).isEqualTo(myCreatedLabel.getFavorite());
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

        // Получаем все созданные метки
        List<LabelResponseModel> myCreatedLabels = labelsApi.getAllLabels();


        // TODO : сделать проверку - точно ли ВСЁ нужно проверять? Всё ли я создаю?
        // Убираем из массива наших меток id (для корректного сравнения в будущем, сам id нам не интересен)
        for(LabelResponseModel myCreatedLabel : myCreatedLabels) {
            myCreatedLabel.setId("");
        }
        List<LabelResponseModel> templatesLabels = PROJECT_TEMPLATES .getLabels();
        assertThat(templatesLabels).containsExactlyInAnyOrderElementsOf(myCreatedLabels); // проверка двух листов без учета порядка
        /*for(int i = 0; i < templatesLabels.size(); i++) { // TODO : сильно под вопросом, что мои метки и шаблонные будут в одинаковом порядке
            assertThat(templatesLabels.get(i).getName()).isEqualTo(myCreatedLabels.get(i).getName());
            assertThat(templatesLabels.get(i).getColor()).isEqualTo(myCreatedLabels.get(i).getColor());
            assertThat(templatesLabels.get(i).getOrder()).isEqualTo(myCreatedLabels.get(i).getOrder());
            assertThat(templatesLabels.get(i).getFavorite()).isEqualTo(myCreatedLabels.get(i).getFavorite());
        }*/
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
        
        // TODO : проверяем, что я могу получить такую метку?
        //

        // Удаляем метку!
        labelsApi.deleteLabel(labelId);

        // TODO : проверяем, что более по такому ID метка недоступна.
        //
    }

    // TODO : Создание метки с уже имеющимся именем? Код ответа 400
}
