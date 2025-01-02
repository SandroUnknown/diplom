package pages;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import enums.ViewStyle;
import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ProjectPage {

    private SelenideElement getSelectProjectColorElement(Color projectColor) {
        return $(String.format("[data-value='%s']", projectColor.getTitle()));
    }

    private SelenideElement getSelectViewStyleElement(ViewStyle viewStyle) {
        return $(String.format("#%s", viewStyle)).sibling(0);
    }

    private SelenideElement getProjectColorCheckElement(Color projectColor) {
        String projectColorElement = String.format("[style='color: var(--named-color-%s);']", projectColor.getCssTitle());
        return projectListElement.$(projectColorElement);
    }

    private final SelenideElement
            plusButtonElement = $("#content button[aria-label='Мои проекты']"),
            addProjectButtonElement = $("[aria-label='Добавить проект']"),
            projectNameInputElement = $("input[name='name']"),
            projectColorDropdownElement = $("button[aria-labelledby='edit_project_modal_field_color_label']"),
            addToFavoriteElement = $("input[name='is_favorite']").parent().parent(),
            createProjectButtonElement = $("button[type='submit']"),
            projectListElement = $("#projects_list");

    @Step("Нажать '+'.")
    public ProjectPage clickPlusButton() {
        plusButtonElement.click();
        return this;
    }

    @Step("Нажать 'Добавить проект'.")
    public ProjectPage clickAddProject() {
        addProjectButtonElement.click();
        return this;
    }

    @Step("Ввести имя проекта.")
    public ProjectPage inputProjectName(String projectName) {
        projectNameInputElement.setValue(projectName);
        return this;
    }

    @Step("Выбрать цвет проекта.")
    public ProjectPage selectProjectColor(Color projectColor) {
        projectColorDropdownElement.click();
        getSelectProjectColorElement(projectColor).click();
        return this;
    }

    @Step("Добавить проект в 'Избранное'.")
    public ProjectPage addToFavorite(boolean isFavorite) {
        if (isFavorite) {
            addToFavoriteElement.click();
        }
        return this;
    }

    @Step("Выбрать стиль отображения проекта.")
    public ProjectPage selectViewStyle(ViewStyle viewStyle) {
        getSelectViewStyleElement(viewStyle).click();
        return this;
    }

    @Step("Нажать кнопку 'Добавить'.")
    public ProjectPage addProject() {
        createProjectButtonElement.click();
        return this;
    }

    @Step("Нажать кнопку 'Добавить'.")
    public ProjectPage checkSuccessfulCreateProject(ProjectRequestModel testProjectData) {

        // Проверка имени
        projectListElement.shouldHave(text(testProjectData.getName()));

        // Проверка цвета
        getProjectColorCheckElement(testProjectData.getColor()).shouldBe(exist);

        // TODO : проверка фаворит

        // TODO : проверка вьюСтайл



        return this;
    }
}