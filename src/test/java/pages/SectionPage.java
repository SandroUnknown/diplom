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
    //private final String path = "/app/projects/active"; // TODO : заменить на правильный
    private final String basePath = "app/project/%s";


    
    private SelenideElement getOtherActionsElement(String sectionId) {
        String str = String.format("header#section-%s div.board_section__menu_trigger button", sectionId);
        return $(str);
    }  

    private final SelenideElement
            addSectionButtonElement = $("button.board_add_section_button"),
            sectionNameInputElement = $("div.board_view__add_section input"),
            createSectionButtonElement = $("div.board_view__add_section button[type='submit']"),
            deleteSectionButtonElement = $$("div.reactist_menulist div").last(),
            confirmDeleteSectionButtonElement = $$("div[data-testid='modal-overlay'] footer button").last(),
            separatorBetweenSectionsElement = $$("div.board_add_section_trigger__container");   // он не СелинидЭлемент !

    private final SelenideElement
            sectionCheckElement = $$("[data-testid='board-section']"),    // он не СелинидЭлемент !
            addProjectButtonElement = $("[aria-label='Добавить проект']"),
            projectNameInputElement = $("input[name='name']");
    


    @Step("Открыть страницу.")
    public SectionPage openPage(String projectId) {
        String path = String.format(basePath, projectId);
        open(path);
        return this;
    }

    @Step("Ввести логин и пароль.")
    public SectionPage login() {
        new AuthPage().login();
        return this;
    }

    @Step("Нажать на кнопку 'Добавить раздел'.")
    public SectionPage clickOnAddSection() {
        addSectionButtonElement.click();
        return this;
    }

    @Step("Навести мышку на разделительную линию между разделами и нажать на неё.")
    public SectionPage clickOnSeparatorBetweenSections(int separatorIndex) {
        //separatorBetweenSectionsElement.get(separatorIndex).hover(); // TODO : надо ли на него наводиться мышкой?
        separatorBetweenSectionsElement.get(separatorIndex).click();
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

    // === ПРОВЕРКИ ================================

    @Step("Проверить, что раздел был успешно создан в указанном месте.")
    public SectionPage checkSuccsessfulCreatedSection(int sectionIndex, String sectionName) {
        sectionCheckElement.get(sectionIndex).shouldHave(text(sectionName));
        return this;
    }

    @Step("Проверить, что раздел был успешно удалён (отсутствует в списке разделов).")
    public SectionPage checkSuccsessfulDeleteSection(String sectionName) {
        sectionCheckElement.forEach(element -> element.shouldNotHave(text(sectionName)));
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
