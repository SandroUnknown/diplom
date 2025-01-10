package screens;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BrowseScreen {

    private final SelenideElement createProjectButtonElement = $(By.xpath(
            "//androidx.recyclerview.widget.RecyclerView[@resource-id='android:id/list']" +
                    "/androidx.compose.ui.platform.ComposeView[3]/android.view.View" +
                    "/android.view.View/android.view.View[1]"));

    public BrowseScreen clickCreateProject() {
        createProjectButtonElement.click();
        return this;
    }
}
