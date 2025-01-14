package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class TaskCreateScreen {

    private final SelenideElement taskNameInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='android:id/message']"));

    private final SelenideElement taskPriorityButtonElement = $(By.xpath(
            "//android.widget.TextView[@content-desc='Priority']"));

    //getTaskPriorityDropdownElement

    private final SelenideElement submitButtonElement = $(By.xpath(
            "//android.widget.ImageView[@resource-id='android:id/button1']"));

    @Step("Ввести название задачи")
    public TaskCreateScreen inputTaskName(String taskName) {
        taskNameInputElement.sendKeys(taskName);
        return this;
    }

    @Step("Установить приоритет задачи")
    public TaskCreateScreen setTaskPriority(int taskPriority) {
        String text = taskNameInputElement.getAttribute("text");
        text = text + " !!" + taskPriority;
        taskNameInputElement.sendKeys(text);


        //clickTaskPriority();
        //selectTaskPriority(taskPriority);
        return this;
    }

    @Step("Нажать на 'Приоритет'")
    private TaskCreateScreen clickTaskPriority() {
        taskPriorityButtonElement.click();
        return this;
    }

    // TODO : дописать в степ параметр
    @Step("Выбрать значение")
    private TaskCreateScreen selectTaskPriority(int taskPriority) {
        return this;
    }

    @Step("Нажать 'Применить'")
    public TaskCreateScreen clickSubmit() {
        submitButtonElement.click();
        return this;
    }
}