package screens.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.back;

public class CreateTaskModalWindow {

    private final SelenideElement taskNameInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='android:id/message']"));

    private final SelenideElement submitButtonElement = $(By.xpath(
            "//android.widget.ImageView[@resource-id='android:id/button1']"));

    @Step("Ввести название задачи")
    public CreateTaskModalWindow inputTaskName(String taskName) {
        taskNameInputElement.sendKeys(taskName);
        return this;
    }

    // TODO : дописать параметр в Степ
    @Step("Установить приоритет задачи")
    public CreateTaskModalWindow setTaskPriority(int taskPriority) {
        String text = taskNameInputElement.getAttribute("text") + " !!" + taskPriority;
        taskNameInputElement.sendKeys(text);
        return this;
    }

    @Step("Нажать 'Применить'")
    public CreateTaskModalWindow clickSubmit(boolean finishCreateTasks) {
        submitButtonElement.click();
        if (finishCreateTasks) {
            back();
        }
        return this;
    }
}