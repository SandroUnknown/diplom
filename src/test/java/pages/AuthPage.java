package pages;

import com.codeborne.selenide.SelenideElement;
import drivers.CredentialsConfigDriver;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage {

    static CredentialsConfigDriver credentials = new CredentialsConfigDriver();

    private final SelenideElement
            emailElement = $("input[type='email']"),
            passwordElement = $("input[type='password']");

    public AuthPage login() {
        emailElement.setValue(credentials.getTodoistEmail());
        passwordElement.setValue(credentials.getTodoistPassword()).pressEnter();
        return this;
    }
}