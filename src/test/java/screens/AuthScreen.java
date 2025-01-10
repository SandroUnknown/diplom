package screens;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class AuthScreen {

    // TODO : вынесли пароли в Овнера
    public static String USER_NAME = "testing.qaguru@gmail.com";
    public static String USER_PASSWORD = "Qwer1234!";

    private final SelenideElement continueWithEmailButtonElement = $(By.xpath("//android.widget.Button[@resource-id='com.todoist:id/btn_email']"));
    private final SelenideElement loginWithEmailButtonElement = $(By.xpath("//android.widget.TextView[@resource-id='com.todoist:id/email_login']"));
    private final SelenideElement emailInputElement = $(By.xpath("//android.widget.EditText[@resource-id='email']"));
    private final SelenideElement passwordInputElement = $(By.xpath("//android.widget.EditText[@resource-id='password']"));
    private final SelenideElement submitLoginButtonElement = $(By.xpath("//android.view.View[@resource-id='auth_button_tag']"));



    public AuthScreen login() {

        continueWithEmailButtonElement.click();
        loginWithEmailButtonElement.click();
        emailInputElement.sendKeys(USER_NAME);
        passwordInputElement.sendKeys(USER_PASSWORD);
        submitLoginButtonElement.click();

        return this;
    }
}
