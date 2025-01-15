package pages;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import enums.CheckField;
import enums.ViewStyle;
import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static enums.CheckField.*;

public class ProjectPage {

    private final String path = "/app/projects/active";

    private SelenideElement getSelectProjectColorElement(Color projectColor) {
        return $(String.format("[data-value='%s']", projectColor.getTitle()));
    }

    private SelenideElement getSelectViewStyleElement(ViewStyle viewStyle) {
        return $(String.format("#%s", viewStyle)).sibling(0);
    }

    // TODO : ???
    private SelenideElement getProjectNameForCheckElement(String projectName) {
        //String projectNameSelector = String.format("a[aria-label] div span", projectName);
        //String projectNameSelector = String.format("a[aria-label] div span", projectName);
        return projectListForCheckElement.$("a[aria-label] div span");
    }

    private SelenideElement getProjectColorForCheckElement(Color projectColor) {
        String projectColorSelector = String.format("[style='color: var(--named-color-%s);']", projectColor.getCssUiTitle());
        return projectListForCheckElement.$(projectColorSelector);
    }

    private SelenideElement getProjectViewStyleForCheckElement(ViewStyle viewStyle) {
        String projectViewStyleSelector = String.format("[data-testid='project-%s-view']", viewStyle.getTitle());
        return $(projectViewStyleSelector);
    }

    private final SelenideElement
            plusButtonElement = $("#content button[aria-label='Мои проекты']"),
            addProjectButtonElement = $("[aria-label='Добавить проект']"),
            projectNameInputElement = $("input[name='name']"),
            projectColorDropdownElement = $("button[aria-labelledby='edit_project_modal_field_color_label']"),
            addToFavoriteElement = $("input[name='is_favorite']").parent().parent(),
            createProjectButtonElement = $("button[type='submit']"),
            projectListForCheckElement = $("ul#projects_list"),
            projectFavoriteForCheckElement = $("div#left-menu-favorites-panel");

    private final SelenideElement
            projectInListElement = $("#content ul[aria-label='Проекты'] li"),
            otherActionsButtonElement = $("button.reactist_menubutton"),
            deleteProjectButtonElement = $$("div.reactist_menulist div").last(),
            confirmDeleteProjectButtonElement = $$("div[data-testid='modal-overlay'] footer button").last(),
            fillProjectListElement = $("#content");

    @Step("Открыть страницу")
    public ProjectPage openPage() {
        open(path);
        return this;
    }

    @Step("Ввести логин и пароль")
    public ProjectPage login() {
        new AuthPage().login();
        return this;
    }

    @Step("Нажать '+'")
    public ProjectPage clickPlusButton() {
        plusButtonElement.click();
        return this;
    }

    @Step("Нажать 'Добавить проект'")
    public ProjectPage clickAddProject() {
        addProjectButtonElement.click();
        return this;
    }

    @Step("Ввести имя проекта : <{projectName}>")
    public ProjectPage inputProjectName(String projectName) {
        projectNameInputElement.setValue(projectName);
        return this;
    }

    @Step("Выбрать цвет проекта : <{projectColor}>")
    public ProjectPage selectProjectColor(Color projectColor) {
        projectColorDropdownElement.click();
        getSelectProjectColorElement(projectColor).click();
        return this;
    }

    @Step("Добавить проект в 'Избранное'")
    public ProjectPage addToFavorite(boolean isFavorite) {
        if (isFavorite) {
            addToFavoriteElement.click();
        }
        return this;
    }

    @Step("Выбрать стиль отображения проекта : <{viewStyle}>")
    public ProjectPage selectProjectViewStyle(ViewStyle viewStyle) {
        getSelectViewStyleElement(viewStyle).click();
        return this;
    }

    @Step("Нажать кнопку 'Добавить'")
    public ProjectPage addProject() {
        createProjectButtonElement.click();
        return this;
    }

    @Step("Навести мышку на проект в списке")
    public ProjectPage mouseHoverOnCreatedProject() {
        projectInListElement.hover();
        return this;
    }

    @Step("Нажать на кнопку 'Другие действия' (три точки справа от названия проекта)")
    public ProjectPage clickOtherActions() {
        otherActionsButtonElement.click();
        return this;
    }

    @Step("Нажать на кнопку 'Удалить'")
    public ProjectPage clickDeleteProjectButton() {
        deleteProjectButtonElement.click();
        return this;
    }

    @Step("Подвердить удаление проекта")
    public ProjectPage clickConfirmDeleteProjectButtonElement() {
        confirmDeleteProjectButtonElement.click();
        return this;
    }

    @Step("Проверить, что проект был корректно создан [UI]")
    public void checkProject(ProjectRequestModel testProjectData, CheckField... checkFields) {

        List<CheckField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(NAME)) {
            checkProjectName(testProjectData.getName());
        }

        if (fieldsList.contains(COLOR)) {
            checkProjectColor(testProjectData.getColor());
        }

        if (fieldsList.contains(FAVORITE)) {
            if (testProjectData.isFavorite()) {
                checkProjectFavorite(testProjectData.getName());
            }
        }

        if (fieldsList.contains(VIEW_STYLE)) {
            checkProjectViewStyle(testProjectData.getViewStyle());
        }
    }

    @Step("Проверить имя созданного проекта")
    private void checkProjectName(String projectName) {
        projectListForCheckElement.shouldHave(text(projectName));
    }

    @Step("Проверить цвет созданного проекта")
    private void checkProjectColor(Color projectColor) {
        getProjectColorForCheckElement(projectColor).shouldBe(exist);
    }

    @Step("Проверить, что созданный проект добавлен в 'Избранное'")
    private void checkProjectFavorite(String projectName) {
        projectFavoriteForCheckElement.shouldHave(text(projectName));
    }

    @Step("Проверить вариант отображения (ViewStyle) созданного проекта")
    private void checkProjectViewStyle(ViewStyle viewStyle) {
        getProjectViewStyleForCheckElement(viewStyle).shouldBe(exist);
    }

    @Step("Проверить, что проект успешно удалён [UI]")
    public void checkDeleteProject() {
        fillProjectListElement.shouldHave(text("0 проектов"));
    }
}
