package tests.api;

import data.DataCreator;
import enums.Color;
import helpers.annotations.CleanupTestData;
import io.qameta.allure.*;
import models.data.TestDataModel;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static enums.CheckField.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через API")
@Feature("Проверка меток через API")
@Tags({@Tag("API"), @Tag("label")})
@DisplayName("Проверка меток через API")
public class LabelTests extends ApiTestBase {

    private final LabelRequestModel testLabelData = LabelRequestModel.builder()
            .name("МЕТКА")
            .color(Color.BLUE)
            .order(2)
            .isFavorite(true)
            .build();
    private final LabelRequestModel updatedTestLabelData = LabelRequestModel.builder()
            .name("ОБНОВЛЁННАЯ МЕТКА")
            .color(Color.RED)
            .order(3)
            .isFavorite(false)
            .build();

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой метки")
    @DisplayName("Создать новую метку (с заполнением только имени)")
    void createNewLabelTest() {

        String labelId = labelsApi.createNewLabel(testLabelData.getName()).getId();

        labelsApi.checkLabel(labelId, testLabelData, NAME);
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание новой метки")
    @DisplayName("Создать новую метку (с заполнением всех параметров)")
    void createNewLabelWithAllParamsTest() {

        String labelId = labelsApi.createNewLabel(testLabelData).getId();

        labelsApi.checkLabel(labelId, testLabelData, NAME, COLOR, ORDER, FAVORITE);
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.BLOCKER)
    @Story("Обновление метки")
    @DisplayName("Обновить метку по ID")
    void updateLabelTest() {

        int templateNumber = 0;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .create();
        String labelId = testData.getLabels().get(0).getId();

        labelsApi.updateLabel(labelId, updatedTestLabelData);

        labelsApi.checkLabel(labelId, updatedTestLabelData, NAME, COLOR, ORDER, FAVORITE);
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение метки")
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

        labelsApi.checkEqualsLabel(myReceivedLabel, myCreatedLabel, NAME, COLOR, ORDER, FAVORITE);
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех меток")
    @DisplayName("Получить все метки пользователя")
    void getAllLabelsTest() {

        int templateNumber = 3;
        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createLabels(true)
                .create();
        List<LabelResponseModel> myCreatedLabels = testData.getLabels();

        List<LabelResponseModel> myReceivedLabels = labelsApi.getAllLabels();

        step("Проверить метки", () -> {
            assertThat(myReceivedLabels.size()).isEqualTo(myCreatedLabels.size());
            for (int i = 0; i < myCreatedLabels.size(); i++) {
                labelsApi.checkEqualsLabel(
                        myReceivedLabels.get(i), myCreatedLabels.get(i),
                        NAME, COLOR, ORDER, FAVORITE);
            }
        });
    }

    @Test
    @CleanupTestData
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление метки")
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
}