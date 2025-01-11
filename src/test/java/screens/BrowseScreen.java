package screens;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BrowseScreen {

    private final SelenideElement createProjectButtonElement = $(By.xpath(
            "//androidx.recyclerview.widget.RecyclerView[@resource-id='android:id/list']" +
                    "/androidx.compose.ui.platform.ComposeView[3]/android.view.View" +
                    "/android.view.View/android.view.View[1]"));

    private final SelenideElement moreOptionsButtonElement = $(By.xpath(
            "//android.widget.ImageView[@content-desc='More options']"));

    private final SelenideElement editButtonElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/title' and @text='Edit']"));

    public BrowseScreen clickCreateProject() {
        createProjectButtonElement.click();
        return this;
    }

    public BrowseScreen clickEditProject() {
        moreOptionsButtonElement.click();
        editButtonElement.click();
        return this;
    }
}