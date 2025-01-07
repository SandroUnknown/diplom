package tests.web;

import api.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataCreator;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.AuthPage;
import pages.ProjectPage;
import pages.SectionPage;
import pages.TaskPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class WebTestBase {

    DataCreator data = new DataCreator();

    ProjectsApi projectsApi = new ProjectsApi();
    SectionsApi sectionsApi = new SectionsApi();
    TasksApi tasksApi = new TasksApi();
    CommentsApi commentsApi = new CommentsApi();
    LabelsApi labelsApi = new LabelsApi();



    ProjectPage projectPage = new ProjectPage();
    SectionPage sectionPage = new SectionPage();
    TaskPage taskPage = new TaskPage();

    AuthPage authPage = new AuthPage();

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "https://api.todoist.com/rest/v2/";


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.pageLoadStrategy = "eager";
        Configuration.browserCapabilities = capabilities;

        /*String login = System.getProperty("login");
        String rwhost = System.getProperty("rwhost");
        if (login != null && rwhost != null) Configuration.remote = format("https://%s@%s/wd/hub", login, rwhost);*/

        Configuration.baseUrl = "https://app.todoist.com";

        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "125.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");

        Configuration.timeout = 7000;

        //Configuration.holdBrowserOpen = true;
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

    @AfterEach
    void cleanupTestData() {

        projectsApi.deleteProjects();
        labelsApi.deleteLabels();
    }
}
