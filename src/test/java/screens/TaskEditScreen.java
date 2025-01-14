package screens;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import enums.ProjectField;
import enums.ViewStyle;
import io.qameta.allure.Step;
import models.projects.ProjectRequestModel;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static enums.ProjectField.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TaskEditScreen {

    private SelenideElement getViewStyleElement(int number) {
        String str = "//android.view.ViewGroup[@resource-id='com.todoist:id/view_style']" +
                "/android.view.View/android.view.View/android.view.View[%s]";
        return $(By.xpath(String.format(str, number)));
    }

    private final SelenideElement applyButtonElement = $(By.xpath(
            "//android.widget.Button[@content-desc='Done']"));


    @Step("Ввести имя проекта")
    public TaskEditScreen inputProjectName(String name) {
        //nameInputElement.sendKeys(name);
        return this;
    }

}