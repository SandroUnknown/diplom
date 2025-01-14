package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SectionEditScreen {

    private final SelenideElement sectionNameInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='android:id/message']"));

    @Step("Нажать '+' (создать проект)")
    public SectionEditScreen inputSectionName(String sectionName) {
        sectionNameInputElement.sendKeys(sectionName);
        return this;
    }
}