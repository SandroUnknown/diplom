package tests.mobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataStorage;
import drivers.ApiConfigDriver;
import drivers.BrowserstackConfigDriver;
import drivers.EmulationConfigDriver;
import io.qameta.allure.selenide.AllureSelenide;
import models.data.TestDataModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import screens.*;
import screens.components.BottomMenu;
import screens.components.CreateSectionModalWindow;
import screens.components.CreateTaskModalWindow;

import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class MobileTestBase {

    static List<TestDataModel> TEMPLATES = new DataStorage("data/Templates.json").getTemplates();;

    AuthScreen authScreen = new AuthScreen();
    BottomMenu bottomMenu = new BottomMenu();
    BrowseScreen browseScreen = new BrowseScreen();
    ProjectScreen projectScreen = new ProjectScreen();
    ProjectEditScreen projectEditScreen = new ProjectEditScreen();
    CreateSectionModalWindow createSectionModalWindow = new CreateSectionModalWindow();
    CreateTaskModalWindow createTaskModalWindow = new CreateTaskModalWindow();
    TaskEditScreen taskEditScreen = new TaskEditScreen();

    @BeforeAll
    static void beforeAll() {

        new ApiConfigDriver();

        String env = System.getProperty("env", "local");
        if (env.equals("remote")) {
            Configuration.browser = BrowserstackConfigDriver.class.getName();
        } else {
            Configuration.browser = EmulationConfigDriver.class.getName();
        }

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