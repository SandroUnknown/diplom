package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProjectScreen {

    private SelenideElement getSectionElement(String sectionName) {
        String str = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']";
        return $(By.xpath(String.format(str, sectionName)));
    }

    private SelenideElement getTaskGroupElement(String sectionName) {
        String str = "//android.widget.TextView[@resource-id='android:id/title' and @text='%s']/../../..";
        return $(By.xpath(String.format(str, sectionName)));
    }

    private SelenideElement getAddTaskButtonElement(String sectionName) {
        SelenideElement parentElement = getTaskGroupElement(sectionName);
        return parentElement.$(By.xpath(
                ".//android.widget.TextView[@resource-id='com.todoist:id/text' and @text='Add task']"));
    }

    private SelenideElement getTaskElement(String sectionName, String taskName) {
        SelenideElement parentElement = getTaskGroupElement(sectionName);
        String str = ".//android.widget.TextView[@resource-id='com.todoist:id/text' and @text='%s']";
        return parentElement.$(By.xpath(String.format(str, taskName)));
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
    public ProjectScreen clickAddTask(String sectionName) {
        getAddTaskButtonElement(sectionName).click();
        return this;
    }

    // TODO : параметр в Степ
    @Step("Нажать на задачу")
    public ProjectScreen clickOnTask(String sectionName, String taskName) {
        getTaskElement(sectionName, taskName).click();
        return this;
    }

    @Step("Проверить раздел")
    public void checkSection(String expectedSectionName) {
        String actualSectionName = getSectionElement(expectedSectionName).getAttribute("text");
        assertThat(actualSectionName).isEqualTo(expectedSectionName);
    }
}