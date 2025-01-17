package screens;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import enums.CheckField;
import enums.ViewStyle;
import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static enums.CheckField.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProjectEditScreen {

    private SelenideElement getViewStyleElement(int number) {
        String str = "//android.view.ViewGroup[@resource-id='com.todoist:id/view_style']" +
                "/android.view.View/android.view.View/android.view.View[%s]";
        return $(By.xpath(String.format(str, number)));
    }

    private SelenideElement getSelectProjectColorElement(Color projectColor) {
        String str = "//android.widget.TextView[@text='%s']";
        String color = projectColor.getCssAndroidTitle();
        return $(By.xpath(String.format(str, color)));
    }

    private final SelenideElement applyButtonElement = $(By.xpath(
            "//android.widget.Button[@content-desc='Done']"));

    private final SelenideElement nameInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='com.todoist:id/name']"));

    private final SelenideElement colorSelectElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/color']"));

    private final SelenideElement favoriteSelectElement = $(By.xpath(
            "//android.widget.Switch[@resource-id='com.todoist:id/favorite']"));

    @Step("Ввести имя проекта : <{projectName}>")
    public ProjectEditScreen inputProjectName(String projectName) {
        nameInputElement.sendKeys(projectName);
        return this;
    }

    @Step("Выбрать цвет проекта : <{color}>")
    public ProjectEditScreen selectProjectColor(Color color) {
        colorSelectElement.click();
        getSelectProjectColorElement(color).click();
        return this;
    }

    @Step("Добавить проект в 'Избранное'")
    public ProjectEditScreen addToFavorite(boolean isFavorite) {
        if (isFavorite) {
            favoriteSelectElement.click();
        }
        return this;
    }

    @Step("Выбрать стиль отображения проекта : <{viewStyle}>")
    public ProjectEditScreen selectProjectViewStyle(ViewStyle viewStyle) {
        int numberViewStyle = viewStyle.getNumber();
        getViewStyleElement(numberViewStyle).click();
        return this;
    }

    @Step("Нажать кнопку 'Добавить' (галочка в правом верхнем углу)")
    public ProjectEditScreen clickApplyButtonElement() {
        applyButtonElement.click();
        return this;
    }

    @Step("Проверить, что проект был корректно создан")
    public void checkProject(ProjectRequestModel testProjectData, CheckField... checkFields) {

        List<CheckField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(NAME)) {
            checkProjectName(testProjectData.getName());
        }

        if (fieldsList.contains(COLOR)) {
            checkProjectColor(testProjectData.getColor());
        }

        if (fieldsList.contains(FAVORITE)) {
            checkProjectFavorite(testProjectData.isFavorite());
        }

        if (fieldsList.contains(VIEW_STYLE)) {
            checkProjectViewStyle(testProjectData.getViewStyle());
        }
    }

    @Step("Проверить имя созданного проекта")
    private void checkProjectName(String expectedProjectName) {
        String actualProjectName = nameInputElement.getAttribute("text");
        assertThat(actualProjectName).isEqualTo(expectedProjectName);
    }

    @Step("Проверить цвет созданного проекта")
    private void checkProjectColor(Color expectedProjectColor) {
        String actualProjectColor = colorSelectElement.getAttribute("text");
        assertThat(actualProjectColor).isEqualTo(expectedProjectColor.getCssAndroidTitle());
    }

    @Step("Проверить, что созданный проект добавлен в 'Избранное'")
    private void checkProjectFavorite(boolean expectedFavorite) {
        boolean actualFavorite = Boolean.parseBoolean(favoriteSelectElement.getAttribute("checked"));
        assertThat(actualFavorite).isEqualTo(expectedFavorite);
    }

    @Step("Проверить вариант отображения (ViewStyle) созданного проекта")
    private void checkProjectViewStyle(ViewStyle viewStyle) {
        int numberViewStyle = viewStyle.getNumber();
        boolean actualViewStyle = Boolean.parseBoolean(
                getViewStyleElement(numberViewStyle).getAttribute("checked"));
        assertThat(actualViewStyle).isEqualTo(true);
    }
}