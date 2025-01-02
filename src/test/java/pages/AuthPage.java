package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthPage {

    public static String USER_NAME = "testing.qaguru@gmail.com";
    public static String USER_PASSWORD = "Qwer1234!";

    private final SelenideElement
            emailElement = $("input[type='email']"),
            passwordElement = $("input[type='password']");


    public AuthPage login(String path) {

        //open("/app/today");
        open(path);

        emailElement.setValue(USER_NAME);
        passwordElement.setValue(USER_PASSWORD).pressEnter();

        return this;
    }
}
