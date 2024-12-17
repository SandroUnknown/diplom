package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static java.lang.String.format;

public class TestBase {

    @BeforeAll
    public static void setUp() {

        // testing.qaguru@gmail.com
        // Asdf1234!
        // Qwer1234!
        // token = 62d652154d66834e51a6b776fd6f4fa79ab6e4a0




        RestAssured.baseURI = "https://api.todoist.com/rest/v2";
        //RestAssured.basePath =


        /*DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.pageLoadStrategy = "eager";
        Configuration.browserCapabilities = capabilities;*/

        /*String login = System.getProperty("login");
        String rwhost = System.getProperty("rwhost");
        if (login != null && rwhost != null) Configuration.remote = format("https://%s@%s/wd/hub", login, rwhost);*/

       /* Configuration.baseUrl = "https://api.todoist.com/rest/v2";

        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "125.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");*/
    }

    @BeforeEach
    void preTest() {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {

        /*Attach.screenshotAs("Last screenshot");
        if (!Configuration.browser.equals("firefox")) {
            Attach.pageSource();
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();*/
        closeWebDriver();
    }
}