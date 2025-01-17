package screens;

import com.codeborne.selenide.SelenideElement;
import drivers.CredentialsConfigDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class AuthScreen {

    static final CredentialsConfigDriver credentials = new CredentialsConfigDriver();

    private final SelenideElement continueWithEmailButtonElement = $(By.xpath(
            "//android.widget.Button[@resource-id='com.todoist:id/btn_email']"));

    private final SelenideElement loginWithEmailButtonElement = $(By.xpath(
            "//android.widget.TextView[@resource-id='com.todoist:id/email_login']"));

    private final SelenideElement emailInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='email']"));

    private final SelenideElement passwordInputElement = $(By.xpath(
            "//android.widget.EditText[@resource-id='password']"));

    private final SelenideElement submitLoginButtonElement = $(By.xpath(
            "//android.view.View[@resource-id='auth_button_tag']"));

    @Step("Ввести логин и пароль")
    public AuthScreen login() {

        continueWithEmailButtonElement.click();
        loginWithEmailButtonElement.click();
        emailInputElement.sendKeys(credentials.getTodoistEmail());
        passwordInputElement.sendKeys(credentials.getTodoistPassword());
        submitLoginButtonElement.click();

        return this;
    }
}
