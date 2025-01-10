package helpers.annotations;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import pages.AuthPage;

import java.util.HashSet;
import java.util.Set;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WithLoginExtension implements BeforeEachCallback {

    // TODO : убрать отсюда эти переменные
    public static String USER_NAME = "testing.qaguru@gmail.com";
    public static String USER_PASSWORD = "Qwer1234!";
    public static Set<Cookie> ALL_COOKIES = new HashSet<>();


    @Override
    public void beforeEach(ExtensionContext context) {

        /*SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
        AuthPage authPage = new AuthPage();
        authPage.login();*/


        /*SelenideElement emailElement = $("input[type='email']");
        SelenideElement passwordElement = $("input[type='password']");

        //open("/auth/login");
        open("/app/today");

        emailElement.setValue(USER_NAME);
        passwordElement.setValue(USER_PASSWORD).pressEnter();

        ALL_COOKIES = WebDriverRunner.getWebDriver().manage().getCookies();*/



        //USER_NAME = System.getProperty("storeUserName", "login");
        //USER_PASSWORD = System.getProperty("storeUserPassword", "password");

        /*LoginResponseModel authResponse = OLD_AuthorizationApi.getAuthData(USER_NAME, USER_PASSWORD);

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));

        USER_ID = authResponse.getUserId();
        USER_TOKEN = authResponse.getToken();
        EXPIRES = authResponse.getExpires();
        CREATE_DATE = authResponse.getCreatedDate();
        IS_ACTIVE = authResponse.getIsActive();*/


       //===============================================

        /*
        open("/app/today");
        $("[type='email]").setValue(USER_NAME);
        $("#element-3").setValue(USER_PASSWORD).pressEnter();*/
    }
}