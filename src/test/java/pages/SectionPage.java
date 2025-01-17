package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SectionPage {

    private final SelenideElement
            addSectionButtonElement = $("button.board_add_section_button"),
            sectionNameInputElement = $("input[aria-label='Название раздела']"),
            createSectionButtonElement = $("button[type='submit']"),
            deleteSectionButtonElement = $$("div.reactist_menulist div").last(),
            confirmDeleteSectionButtonElement = $$("div[data-testid='modal-overlay'] footer button").last();

    private final ElementsCollection
            separatorBetweenSectionsElement = $$("button.board_add_section_trigger"),
            sectionCheckElement = $$("[data-testid='board-section']"),
            otherActionsElement = $$("div.board_section__menu_trigger button");

    @Step("Открыть страницу")
    public SectionPage openPage(String url) {
        open(url);
        return this;
    }

    @Step("Ввести логин и пароль")
    public SectionPage login() {
        new AuthPage().login();
        return this;
    }

    @Step("Нажать на кнопку 'Добавить раздел'")
    public SectionPage clickOnAddSection() {
        addSectionButtonElement.click();
        return this;
    }

    @Step("Навести мышку на разделительную линию между разделами и нажать на неё")
    public SectionPage clickOnSeparatorBetweenSections(int separatorIndex) {
        separatorBetweenSectionsElement.get(separatorIndex).click();
        return this;
    }
    
    @Step("Ввести имя раздела : <{sectionName}>")
    public SectionPage inputSectionName(String sectionName) {
        sectionNameInputElement.setValue(sectionName);
        return this;
    }

    @Step("Нажать кнопку 'Добавить раздел'")
    public SectionPage addSection() {
        createSectionButtonElement.click();
        return this;
    }

    @Step("Нажать на кнопку 'Другие действия' (три точки справа от названия раздела)")
    public SectionPage clickOtherActions(int sectionNumberToDelete) {
        otherActionsElement.get(sectionNumberToDelete).click();
        return this;
    }

    @Step("Нажать на кнопку 'Удалить'.")
    public SectionPage clickDeleteSectionButton() {
        deleteSectionButtonElement.click();
        return this;
    }

    @Step("Подвердить удаление раздела")
    public SectionPage clickConfirmDeleteSectionButtonElement() {
        confirmDeleteSectionButtonElement.click();
        return this;
    }

    @Step("Проверить, что раздел был успешно создан в указанном месте [UI]")
    public void checkSection(int sectionIndex, String sectionName) {
        sectionCheckElement.get(sectionIndex).shouldHave(text(sectionName));
    }

    @Step("Проверить, что раздел был успешно удалён [UI]")
    public void checkDeleteSection(String sectionName) {
        sectionCheckElement.forEach(element -> element.shouldNotHave(text(sectionName)));
    }
}