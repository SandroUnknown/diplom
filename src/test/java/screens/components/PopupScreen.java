package screens.components;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class PopupScreen {

    /*private final SelenideElement popupYesButtonElement = $(By.xpath(
            "//android.widget.Button[@resource-id='android:id/button3']"));*/

    private final SelenideElement popupYesButtonElement = $(
            "android:id/button3");

    public PopupScreen clickYesButton() {
        if (popupYesButtonElement.isDisplayed()) {
            popupYesButtonElement.click();
        }
        return this;
    }
}
