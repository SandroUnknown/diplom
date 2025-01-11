package tests.mobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserstackDriver;
//import drivers.EmulationDriver;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import screens.AuthScreen;
import screens.BrowseScreen;
import screens.EditProjectScreen;
import screens.ProjectScreen;
import screens.components.BottomMenu;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class MobileTestBase {

    AuthScreen authScreen = new AuthScreen();
    BottomMenu bottomMenu = new BottomMenu();
    BrowseScreen browseScreen = new BrowseScreen();
    ProjectScreen projectScreen = new ProjectScreen();
    EditProjectScreen editProjectScreen = new EditProjectScreen();


    @BeforeAll
    static void beforeAll() {

        // удалить потом хардкод
        Configuration.browser = BrowserstackDriver.class.getName();

        /*String env = System.getProperty("env", "local");
        if (env.equals("remote")) {
            Configuration.browser = BrowserstackDriver.class.getName();
        } else {
            Configuration.browser = EmulationDriver.class.getName();
        }*/


        // TODO : это вероятно не нужно))
        /*if (System.getProperty("deviceHost").equals("emulation")) {
            Configuration.browser = EmulationDriver.class.getName();
        } else if (System.getProperty("deviceHost").equals("browserstack")) {
            Configuration.browser = BrowserstackDriver.class.getName();
        }*/

        Configuration.browserSize = null;
        Configuration.timeout = 30000;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() {
        //Attach attach = new Attach();
       /* if (System.getProperty("deviceHost").equals("browserstack")) {
            String sessionId = Selenide.sessionId().toString();
            attach.addVideo(sessionId);
        }
        if (System.getProperty("deviceHost").equals("emulation")) {
            attach.screenshotAs("Last screenshot");
        }*/
        //Attach.pageSource();
        closeWebDriver();
    }
}