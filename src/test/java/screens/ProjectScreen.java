package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ProjectScreen {

    private final SelenideElement moreOptionsButtonElement = $(By.xpath(
            "//android.widget.ImageView[@content-desc='More options']"));

    private final SelenideElement editButtonElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/title' and @text='Edit']"));

    @Step("Нажать 'редактировать проект'")
    public ProjectScreen clickEditProject() {
        moreOptionsButtonElement.click();
        editButtonElement.click();
        return this;
    }
}