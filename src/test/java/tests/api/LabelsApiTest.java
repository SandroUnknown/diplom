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

// TODO : может быть тест переменные передавать как параметры?

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
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой метки (с заполнением только имени)")
    @DisplayName("Создать новую метку (с заполнением только имени)")
    void createNewLabelTest() {

        LabelResponseModel myCreatedLabel = labelsApi.createNewLabel(testLabelData.getName());

        step("Проверить, что метка была корректно создана", () -> {
            assertThat(myCreatedLabel.getName()).isEqualTo(testLabelData.getName());
            assertThat(myCreatedLabel.getColor()).isEqualTo(defaultColor);
            assertThat(myCreatedLabel.isFavorite()).isEqualTo(defaultFavorite);
        });
    }

    // TODO : сделать параметризованным?
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой метки (с заполнением всех параметров)")
    @DisplayName("Создать новую метку (с заполнением всех параметров)")
    void createNewLabelWithAllParamsTest() {

        LabelResponseModel myCreatedLabel = labelsApi.createNewLabel(testLabelData);

        step("Проверить, что метка была корректно создана", () -> {
            assertThat(myCreatedLabel.getName()).isEqualTo(testLabelData.getName());
            assertThat(myCreatedLabel.getColor()).isEqualTo(testLabelData.getColor());
            assertThat(myCreatedLabel.getOrder()).isEqualTo(testLabelData.getOrder());
            assertThat(myCreatedLabel.isFavorite()).isEqualTo(testLabelData.isFavorite());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
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

        step("Проверить, что метка была корректно обновлена", () -> {
            assertThat(myUpdatedLabel.getName()).isEqualTo(newLabelName);
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
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

        step("Проверить, что метка была корректно получена", () -> {
            assertThat(myReceivedLabel.getName()).isEqualTo(myCreatedLabel.getName());
            assertThat(myReceivedLabel.getColor()).isEqualTo(myCreatedLabel.getColor());
            assertThat(myReceivedLabel.getOrder()).isEqualTo(myCreatedLabel.getOrder());
            assertThat(myReceivedLabel.isFavorite()).isEqualTo(myCreatedLabel.isFavorite());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
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

        step("Проверить, что метки были корректно получены", () -> {
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
    @Severity(SeverityLevel.NORMAL)
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

        step("Проверить, что метка действительно была удалена", () -> {
            int currentLabelCount = labelsApi.getAllLabels().size();
            assertThat(currentLabelCount).isEqualTo(createdLabelCount - 1);
        });
    }

    // TODO : Создание метки с уже имеющимся именем? Код ответа 400
}
