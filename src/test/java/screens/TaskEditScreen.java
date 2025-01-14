package screens;

import com.codeborne.selenide.SelenideElement;
import enums.CheckField;
import io.qameta.allure.Step;
import models.tasks.TaskRequestModel;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static enums.CheckField.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TaskEditScreen {

    private final SelenideElement taskContentElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='com.todoist:id/item_content']"));

    private final SelenideElement taskPriorityElement = $(By.xpath(
            "//android.view.View[@content-desc='Priority']" +
                    "/following-sibling::android.widget.TextView"));

    @Step("Проверить, что задача была корректно создана")
    public void checkTask(TaskRequestModel testTaskData, CheckField... checkFields) {

        List<CheckField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(CONTENT)) {
            checkTaskContent(testTaskData.getContent());
        }

        if (fieldsList.contains(PRIORITY)) {
            checkTaskPriority(testTaskData.getPriority());
        }
    }

    // TODO : параметр в степ
    @Step("Проверить, что созданная задача имеет верное название")
    private void checkTaskContent(String expectedContent) {
        /*String actualContent = taskContentElement.getAttribute("text").strip();
        expectedContent = expectedContent.strip();*/
        String actualContent = taskContentElement.getAttribute("text");
        actualContent = actualContent.replaceAll("\\u200B", "");

        //expectedContent = expectedContent.replaceAll("\\u200B", "");

        assertThat(actualContent).isEqualTo(expectedContent);
    }

    // TODO : параметр в степ
    @Step("Проверить, что созданная задача имеет верный приоритет")
    private void checkTaskPriority(int expectedPriority) {
        String actualStringPriority = taskPriorityElement.getAttribute("text");
        int actualPriority = Integer.parseInt(
                actualStringPriority.replaceAll("[^0-9]", ""));
        assertThat(actualPriority).isEqualTo(expectedPriority);
    }
}