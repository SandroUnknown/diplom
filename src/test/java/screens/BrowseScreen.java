package screens;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BrowseScreen {

    /*private final SelenideElement createProjectButtonElement = $(By.xpath(
            "//androidx.recyclerview.widget.RecyclerView[@resource-id='android:id/list']" +
                    "/androidx.compose.ui.platform.ComposeView[3]/android.view.View" +
                    "/android.view.View/android.view.View[1]"));*/

    private final SelenideElement createProjectButtonElement = $(By.xpath(
            "//android.view.View[@content-desc='Add']"));

    @Step("Нажать '+' (создать проект)")
    public BrowseScreen clickCreateProject() {
        createProjectButtonElement.click();
        return this;
    }
}