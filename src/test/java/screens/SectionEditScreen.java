package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SectionEditScreen {

    private final SelenideElement sectionNameInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='android:id/message']"));

    private final SelenideElement submitButtonElement = $(By.xpath(
            "//android.widget.ImageButton[@content-desc='Submit']"));

    @Step("Ввести имя раздела")
    public SectionEditScreen inputSectionName(String sectionName) {
        sectionNameInputElement.sendKeys(sectionName);
        sectionNameInputElement.pressEnter();
        //submitButtonElement.click();
        return this;
    }
}