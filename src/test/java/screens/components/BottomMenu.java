package screens.components;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BottomMenu {

    private SelenideElement getMenuElement(int number) {
        String str = "//androidx.compose.ui.platform.ComposeView[@resource-id='com.todoist:id/compose_navigation_holder']/android.view.View/android.view.View/android.view.View/android.view.View[%s]";
        return $(By.xpath(String.format(str, number)));
    }

    public BottomMenu clickToday() {
        getMenuElement(1).click();
        return this;
    }

    public BottomMenu clickInbox() {
        getMenuElement(2).click();
        return this;
    }

    public BottomMenu clickSearch() {
        getMenuElement(3).click();
        return this;
    }

    public BottomMenu clickBrowse() {
        getMenuElement(4).click();
        return this;
    }



}
