package screens;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ProjectScreen {

    private SelenideElement getSectionNameElement(String sectionName) {
        String str = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']";
        return $(By.xpath(String.format(str, sectionName)));
    }

    private final SelenideElement moreOptionsButtonElement = $(By.xpath(
            "//android.widget.ImageView[@content-desc='More options']"));

    private final SelenideElement editProjectButtonElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/title' and @text='Edit']"));

    private final SelenideElement addSectionButtonElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/title' and @text='Add section']"));

    @Step("Нажать 'Больше опций'")
    public ProjectScreen clickMoreOptions() {
        moreOptionsButtonElement.click();
        return this;
    }

    @Step("Нажать 'Редактировать проект'")
    public ProjectScreen clickEditProject() {
        editProjectButtonElement.click();
        return this;
    }

    @Step("Нажать 'Добавить раздел'")
    public ProjectScreen clickAddSection() {
        addSectionButtonElement.click();
        return this;
    }

    @Step("Проверить раздел")
    public ProjectScreen checkSection(String sectionName) {
        getSectionNameElement(sectionName).click();
        return this;
    }


}