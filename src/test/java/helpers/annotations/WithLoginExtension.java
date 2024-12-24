package helpers.annotations;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.Selenide.open;

public class WithLoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
/*
        USER_NAME = System.getProperty("storeUserName", "login");
        USER_PASSWORD = System.getProperty("storeUserPassword", "password");

        LoginResponseModel authResponse = OLD_AuthorizationApi.getAuthData(USER_NAME, USER_PASSWORD);

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));

        USER_ID = authResponse.getUserId();
        USER_TOKEN = authResponse.getToken();
        EXPIRES = authResponse.getExpires();
        CREATE_DATE = authResponse.getCreatedDate();
        IS_ACTIVE = authResponse.getIsActive();*/
    }
}