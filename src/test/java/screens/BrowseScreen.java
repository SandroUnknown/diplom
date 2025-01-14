package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BrowseScreen {

    private SelenideElement getProjectElement(String projectName) {
        String str = "(//android.widget.TextView[@text='%s'])[2]";
        return $(By.xpath(String.format(str, projectName)));
    }

    private final SelenideElement createProjectButtonElement = $(By.xpath(
            "//android.view.View[@content-desc='Add']"));

    private final SelenideElement showProjectButtonElement = $(By.xpath(
            "(//android.view.View[@content-desc='Expand/collapse'])[2]"));

    @Step("Нажать '+' (создать проект)")
    public BrowseScreen clickCreateProject() {
        createProjectButtonElement.click();
        return this;
    }

    @Step("Нажать 'v' (показать проекты)")
    public BrowseScreen clickShowProject() {
        showProjectButtonElement.click();
        return this;
    }

    // TODO : подставить в степ название проекта из параметра
    @Step("Открыть проект")
    public BrowseScreen openProject(String projectName) {
        getProjectElement(projectName).click();
        return this;
    }
}