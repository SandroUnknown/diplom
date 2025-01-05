package pages;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import enums.ViewStyle;
import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SectionPage {

    // TODO : пас и прочие переменные
    private final String path = "/app/projects/active"; // TODO : заменить на правильный


    
    private SelenideElement getOtherActionsElement(String sectionId) {
        String str = String.format("header#section-%s div.board_section__menu_trigger button", sectionId);
        return $(str);
    }  

    private final SelenideElement
            addSectionButtonElement = $("button.board_add_section_button"),
            sectionNameInputElement = $("div.board_view__add_section input"),
            createSectionButtonElement = $("div.board_view__add_section button[type='submit']"),
            deleteSectionButtonElement = $$("div.reactist_menulist div").last(),
            confirmDeleteSectionButtonElement = $$("div[data-testid='modal-overlay'] footer button").last();

    

    /*private final SelenideElement
            plusButtonElement = $("#content button[aria-label='Мои проекты']"),
            addProjectButtonElement = $("[aria-label='Добавить проект']"),
            projectNameInputElement = $("input[name='name']"),
            projectColorDropdownElement = $("button[aria-labelledby='edit_project_modal_field_color_label']"),
            addToFavoriteElement = $("input[name='is_favorite']").parent().parent(),
            createProjectButtonElement = $("button[type='submit']"),
            projectListForCheckElement = $("ul#projects_list"),
            projectFavoriteForCheckElement = $("div#left-menu-favorites-panel");*/

    /*private final SelenideElement
            projectInListElement = $("#content ul[aria-label='Проекты'] li"),
            otherActionsButtonElement = $("button.reactist_menubutton"),
            deleteProjectButtonElement = $$("div.reactist_menulist div").last(),
            confirmDeleteProjectButtonElement = $$("div[data-testid='modal-overlay'] footer button").last(),
            fillProjectListElement = $("#content");*/

    /*@Step("Открыть страницу.")
    public ProjectPage openPage() {
        open(path);
        return this;
    }

    @Step("Ввести логин и пароль.")
    public ProjectPage login() {
        new AuthPage().login();
        return this;
    }*/

    @Step("Нажать на кнопку 'Добавить раздел'.")
    public SectionPage clickOnAddSection() {
        addSectionButtonElement.click();
        return this;
    }
    
    @Step("Ввести имя раздела.")
    public SectionPage inputSectionName(String sectionName) {
        sectionNameInputElement.setValue(sectionName);
        return this;
    }

    @Step("Нажать кнопку 'Добавить раздел'.")
    public SectionPage addSection() {
        createSectionButtonElement.click();
        return this;
    }

    @Step("Нажать на кнопку 'Другие действия' (три точки справа от названия раздела).")
    public SectionPage clickOtherActions(String sectionId) {
        getOtherActionsElement(sectionId).click();
        return this;
    }

    @Step("Нажать на кнопку 'Удалить'.")
    public SectionPage clickDeleteSectionButton() {
        deleteSectionButtonElement.click();
        return this;
    }

    @Step("Подвердить удаление раздела.")
    public SectionPage clickConfirmDeleteSectionButtonElement() {
        confirmDeleteSectionButtonElement.click();
        return this;
    }


    

    /*
    @Step("Нажать на кнопку 'Другие действия' (три точки справа от названия проекта).")
    public ProjectPage clickOtherActions() {
        otherActionsButtonElement.click();
        return this;
    }

    @Step("Нажать на кнопку 'Удалить'.")
    public ProjectPage clickDeleteProjectButton() {
        deleteProjectButtonElement.click();
        return this;
    }

    @Step("Подвердить удаление проекта.")
    public ProjectPage clickConfirmDeleteProjectButtonElement() {
        confirmDeleteProjectButtonElement.click();
        return this;
    }

    @Step("Проверить, что проект успешно создан.")
    public ProjectPage fullCheckProject(ProjectRequestModel testProjectData) {

        checkProjectName(testProjectData.getName());

        checkProjectColor(testProjectData.getColor());

        if (testProjectData.isFavorite()) {
            checkProjectFavorite(testProjectData.getName());
        }

        checkProjectViewStyle(testProjectData.getViewStyle());

        return this;
    }

    @Step("Проверить имя созданного проекта.")
    public ProjectPage checkProjectName(String projectName) {
        projectListForCheckElement.shouldHave(text(projectName));
        return this;
    }

    @Step("Проверить цвет созданного проекта.")
    public ProjectPage checkProjectColor(Color projectColor) {
        getProjectColorForCheckElement(projectColor).shouldBe(exist);
        return this;
    }

    @Step("Проверить, что созданный проект добавлен в 'Избранное'.")
    public ProjectPage checkProjectFavorite(String projectName) {
        projectFavoriteForCheckElement.shouldHave(text(projectName));
        return this;
    }

    @Step("Проверить вариант отображения (ViewStyle) созданного проекта.")
    public ProjectPage checkProjectViewStyle(ViewStyle viewStyle) {
        getProjectViewStyleForCheckElement(viewStyle).shouldBe(exist);
        return this;
    }

    @Step("Проверить, что проект успешно удалён (отсутствует в списке).")
    public ProjectPage checkDeleteProject() {
        fillProjectListElement.shouldHave(text("0 проектов"));
        return this;
    }*/
}
