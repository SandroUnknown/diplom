package tests.api;

import data.DataCreator;
import enums.Color;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.data.TestDataModel;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : добавить проверки во все тесты
// TODO : дописать теги, овнера и прочие данные

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через API")
@Feature("Проверка меток через API")
@Tags({ @Tag("API"), @Tag("label") })
@DisplayName("Проверка меток через API")
public class LabelsApiTest extends ApiTestBase {

    private final Color defaultColor = Color.CHARCOAL;
    private final boolean defaultFavorite = false;
    private final LabelRequestModel testLabelData = LabelRequestModel.builder()
            .name("МЕТКА")
            .color(Color.BLUE)
            .order(2)
            .isFavorite(true)
            .build();
    private final String newLabelName = "ОБНОВЛЁННАЯ МЕТКА";

    @Test
    @Story("Создание новой метки (с заполнением только имени)")
    @DisplayName("Создать новую метку (с заполнением только имени)")
    void createNewLabelTest() {

        LabelResponseModel myCreatedLabel = labelsApi.createNewLabel(testLabelData.getName());

        step("Проверить, что response-данные соответствуют данным, заданным при создании метки", () -> {
            assertThat(myCreatedLabel.getName()).isEqualTo(testLabelData.getName());
            assertThat(myCreatedLabel.getColor()).isEqualTo(defaultColor);
            assertThat(myCreatedLabel.isFavorite()).isEqualTo(defaultFavorite);
        });
    }

    // TODO : сделать параметризованным?
    @Test
    @Story("Создание новой метки (с заполнением всех параметров)")
    @DisplayName("Создать новую метку (с заполнением всех параметров)")
    void createNewLabelWithAllParamsTest() {

        LabelResponseModel myCreatedLabel = labelsApi.createNewLabel(testLabelData);

        step("Проверить, что response-данные соответствуют данным, заданным при создании метки", () -> {
            assertThat(myCreatedLabel.getName()).isEqualTo(testLabelData.getName());
            assertThat(myCreatedLabel.getColor()).isEqualTo(testLabelData.getColor());
            assertThat(myCreatedLabel.getOrder()).isEqualTo(testLabelData.getOrder());
            assertThat(myCreatedLabel.isFavorite()).isEqualTo(testLabelData.isFavorite());
        });
    }

    @Test
    @Story("Обновление имени метки по ID")
    @DisplayName("Обновить имя метки по ID")
    void updateLabelTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .create();

        String labelId = testData.getLabels().get(0).getId();

        LabelResponseModel myUpdatedLabel = labelsApi.updateLabel(labelId, newLabelName);

        step("Проверить, что response-данные соответствуют данным, заданным при обновлении метки", () -> {
            assertThat(myUpdatedLabel.getName()).isEqualTo(newLabelName);
        });
    }

    @Test
    @Story("Получение метки по ID")
    @DisplayName("Получить метку по ID")
    void getLabelTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .create();

        LabelResponseModel myCreatedLabel = testData.getLabels().get(0);
        String labelId = myCreatedLabel.getId();

        LabelResponseModel myReceivedLabel = labelsApi.getLabel(labelId);

        step("Проверить, что response-данные соответствуют данным, заданным при создании метки", () -> {
            assertThat(myReceivedLabel.getName()).isEqualTo(myCreatedLabel.getName());
            assertThat(myReceivedLabel.getColor()).isEqualTo(myCreatedLabel.getColor());
            assertThat(myReceivedLabel.getOrder()).isEqualTo(myCreatedLabel.getOrder());
            assertThat(myReceivedLabel.isFavorite()).isEqualTo(myCreatedLabel.isFavorite());
        });
    }

    @Test
    @Story("Получение всех меток")
    @DisplayName("Получить все метки пользователя")
    void getAllLabelsTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .create();

        List<LabelResponseModel> myCreatedLabels = testData.getLabels();

        List<LabelResponseModel> myReceivedLabels = labelsApi.getAllLabels();

        step("Проверить, что response-данные соответствуют данным, заданным при создании меток", () -> {
            assertThat(myReceivedLabels.size()).isEqualTo(myCreatedLabels.size());
            for (int i = 0; i < myCreatedLabels.size(); i++) {
                assertThat(myReceivedLabels.get(i).getName()).isEqualTo(myCreatedLabels.get(i).getName());
                assertThat(myReceivedLabels.get(i).getColor()).isEqualTo(myCreatedLabels.get(i).getColor());
                assertThat(myReceivedLabels.get(i).getOrder()).isEqualTo(myCreatedLabels.get(i).getOrder());
                assertThat(myReceivedLabels.get(i).isFavorite()).isEqualTo(myCreatedLabels.get(i).isFavorite());
            }
        });
    }

    @Test
    @Story("Удаление метки по ID")
    @DisplayName("Удалить метку по ID")
    void deleteLabelTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .create();

        int createdLabelCount = testData.getLabels().size();
        String labelId = testData.getLabels().get(0).getId();

        labelsApi.deleteLabel(labelId);

        step("Проверить, что количество меток стало на 1, чем было изначально создано", () -> {
            int currentLabelCount = labelsApi.getAllLabels().size();
            assertThat(currentLabelCount).isEqualTo(createdLabelCount - 1);
        });
    }

    // TODO : Создание метки с уже имеющимся именем? Код ответа 400
}
