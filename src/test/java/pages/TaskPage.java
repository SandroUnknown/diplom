package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import enums.CheckField;
import io.qameta.allure.Step;
import models.tasks.TaskRequestModel;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static enums.CheckField.*;

public class TaskPage {

    private SelenideElement getTaskPriorityDropdownElement(String priority) {
        String str = String.format("li[data-value='%s']", priority);
        return $(str);
    }

    private SelenideElement getTaskContentCheckElement(String taskContent) {
        String str = String.format("div[aria-label='%s'] div[aria-label='Название задачи']", taskContent);
        return $(str);
    }

    private SelenideElement getTaskPriorityCheckElement(String taskContent, int priority) {
        String str = String.format("div[aria-label='%s'] svg[data-priority='%s']", taskContent, 5 - priority);
        return $(str);
    }

    private final SelenideElement
            taskContentInputElement = $("div.task_editor__content_field div.tiptap"),
            taskPriorityButtonElement = $("div[data-priority]"),
            createTaskButtonElement = $("div[data-testid='task-editor-action-buttons'] button[type='submit']"),
            editTaskButtonElement = $("div.board_task__details");

    private final ElementsCollection
            getAddTaskButtonElement = $$("button.plus_add_button");

    @Step("Открыть страницу")
    public TaskPage openPage(String url) {
        open(url);
        return this;
    }

    @Step("Ввести логин и пароль")
    public TaskPage login() {
        new AuthPage().login();
        return this;
    }
    
    @Step("Нажать на кнопку 'Добавить задачу'")
    public TaskPage clickOnAddTask(int taskNumber) {
        getAddTaskButtonElement.get(taskNumber).click();
        return this;
    }

    @Step("Ввести текст задачи : <{taskContent}>")
    public TaskPage inputTaskContent(String taskContent) {
        taskContentInputElement.setValue(taskContent);
        return this;
    }
    
    @Step("Выбрать приоритет задачи : <Приоритет {taskPriority}>")
    public TaskPage selectTaskPriority(String taskPriority) {
        taskPriorityButtonElement.click();
        getTaskPriorityDropdownElement(taskPriority).click();
        return this;
    }

    @Step("Нажать кнопку 'Добавить задачу'")
    public TaskPage addTask() {
        createTaskButtonElement.click();
        return this;
    }

    @Step("Проверить, что задача была корректно создана [UI]")
    public void checkTask(TaskRequestModel testTaskData, CheckField... checkFields) {

        editTaskButtonElement.click();

        List<CheckField> fieldsList = Arrays.asList(checkFields);

        if (fieldsList.contains(CONTENT)) {
            checkTaskContent(testTaskData.getContent());
        }

        if (fieldsList.contains(PRIORITY)) {
            checkTaskPriority(testTaskData.getContent(), testTaskData.getPriority());
        }
    }

    @Step("Проверить содержимое созданной задачи")
    private void checkTaskContent(String taskContent) {
        getTaskContentCheckElement(taskContent).shouldBe(exist);;
    }

    @Step("Проверить приоритет созданной задачи")
    private void checkTaskPriority(String taskContent, int taskPriority) {
        getTaskPriorityCheckElement(taskContent, taskPriority).shouldBe(exist);
    }
}
