package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    private final SelenideElement addTaskButtonElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/text']"));

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

    @Step("Нажать 'Добавить задачу'")
    public ProjectScreen clickAddTask() {
        addTaskButtonElement.click();
        return this;
    }

    @Step("Проверить раздел")
    public void checkSection(String expectedSectionName) {
        String actualSectionName = getSectionNameElement(expectedSectionName).getAttribute("text");
        assertThat(actualSectionName).isEqualTo(expectedSectionName);
    }
}