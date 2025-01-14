package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.back;

public class TaskCreateScreen {

    private final SelenideElement taskNameInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='android:id/message']"));

    private final SelenideElement submitButtonElement = $(By.xpath(
            "//android.widget.ImageView[@resource-id='android:id/button1']"));

    @Step("Ввести название задачи")
    public TaskCreateScreen inputTaskName(String taskName) {
        taskNameInputElement.sendKeys(taskName);
        return this;
    }

    // TODO : дописать параметр в Степ
    @Step("Установить приоритет задачи")
    public TaskCreateScreen setTaskPriority(int taskPriority) {
        String text = taskNameInputElement.getAttribute("text") + " !!" + taskPriority;;
        taskNameInputElement.sendKeys(text);
        return this;
    }

    @Step("Нажать 'Применить'")
    public TaskCreateScreen clickSubmit() {
        submitButtonElement.click();
        back();
        return this;
    }
}