package screens.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BottomMenu {

    private SelenideElement getMenuElement(int number) {
        String str = "//androidx.compose.ui.platform.ComposeView[@resource-id='com.todoist:id/compose_navigation_holder']/android.view.View/android.view.View/android.view.View/android.view.View[%s]";
        return $(By.xpath(String.format(str, number)));
    }

    @Step("Нажать в нижнем меню на 'Сегодня'")
    public BottomMenu clickToday() {
        getMenuElement(1).click();
        return this;
    }

    @Step("Нажать в нижнем меню на 'Входящие'")
    public BottomMenu clickInbox() {
        getMenuElement(2).click();
        return this;
    }

    @Step("Нажать в нижнем меню на 'Поиск'")
    public BottomMenu clickSearch() {
        getMenuElement(3).click();
        return this;
    }

    @Step("Нажать в нижнем меню на 'Обзор'")
    public BottomMenu clickBrowse() {
        getMenuElement(4).click();
        return this;
    }
}