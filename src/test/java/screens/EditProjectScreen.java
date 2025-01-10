package screens;

import com.codeborne.selenide.SelenideElement;
import enums.Color;
import enums.ViewStyle;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class EditProjectScreen {

    private SelenideElement getViewStyleElement(int number) {
        String str = "//android.view.ViewGroup[@resource-id='com.todoist:id/view_style']" +
                "/android.view.View/android.view.View/android.view.View[%s]";
        return $(By.xpath(String.format(str, number)));
    }

    private SelenideElement getSelectProjectColorElement(Color projectColor) {
        String str = "//android.widget.TextView[@text='%s']";
        String color = projectColor.getCssAndroidTitle();
        return $(By.xpath(String.format(str, color)));
    }

    private final SelenideElement applyButtonElement = $(By.xpath("//android.widget.Button[@content-desc='Done']"));
    private final SelenideElement nameInputElement = $(By.xpath("//android.widget.EditText[@resource-id='com.todoist:id/name']"));
    private final SelenideElement colorSelectElement = $(By.xpath("//android.widget.RelativeLayout[@resource-id='com.todoist:id/form_color']"));
    private final SelenideElement favoriteSelectElement = $(By.xpath("//android.widget.RelativeLayout[@resource-id='com.todoist:id/form_favorite']"));


    public EditProjectScreen inputProjectName(String name) {
        nameInputElement.sendKeys(name);
        return this;
    }

    public EditProjectScreen selectProjectColor(Color color) {
        colorSelectElement.click();
        getSelectProjectColorElement(color).click();          // TODO : будет ли работать с VIOLET?
        return this;
    }

    public EditProjectScreen addToFavorite(boolean isFavorite) {
        if (isFavorite) {
            favoriteSelectElement.click();
        }
        return this;
    }

    public EditProjectScreen selectProjectViewStyle(ViewStyle viewStyle) {
        int numberViewStyle = viewStyle.getNumber();
        getViewStyleElement(numberViewStyle).click();
        return this;
    }

    public EditProjectScreen clickApplyButtonElement() {
        applyButtonElement.click();
        return this;
    }



}
