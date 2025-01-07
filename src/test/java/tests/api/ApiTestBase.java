package tests.api;

import api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataCreator;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ApiTestBase {

    // TODO : Точно ли протектед?
    // TODO : Может тут передавать в конструктор ENDPOINT???
    //DataCreator data = new DataCreator();
    DataCreator data = new DataCreator();

    protected ProjectsApi projectsApi = new ProjectsApi();
    protected SectionsApi sectionsApi = new SectionsApi();
    protected TasksApi tasksApi = new TasksApi();
    protected CommentsApi commentsApi = new CommentsApi();
    protected LabelsApi labelsApi = new LabelsApi();

    @BeforeAll
    public static void setUp() {

        // email -      testing.qaguru@gmail.com
        // email pass - Asdf1234!
        // acc pass -   Qwer1234!
        // token -      62d652154d66834e51a6b776fd6f4fa79ab6e4a0

        // =============================================================================

        // 17.12 - 8 часов 30 минут
        // 21.12 - 5 часов 00 минут
        // 22.12 - 1 час 00 минут
        // 23.12 - 3 часа 30 минут (13:00-14:30  //  19:00-20:10  //  20:40-21:30)
        // 24.12 - 7 часов 15 минут (10:25-10:35  //  10:40-12:45  //  13:12-14:35  //  14:55-16:32  //  17:05-19:05)
        // 25.12 - около 3 часов
        // 26.12 - около 3 часов
        // 27.12 - 2 часа 45 минут (11:55-12:10  //  14:30-16:40  //  17:00-17:20)
        // 28.12 - 5 часов 20 минут (10:30-12:55  //  13:10-14:55  //  16:25-17:35)
        // 29.12 - около 2 часов
        // 30.12 - 5 часов 55 минут (10:00-10:45  //  11:20-12:20  //  13:15-15:00  //  15:25-15:55  //  16:25-17:35  //  19:05-19:50)
        // 02.01 - 8 часов 20 минут (12:40-15:50  //  16:15-17:05  //  18:05-19:20  //  19:55-23:00)
        // 03.01 - 2 часа 25 минут (12:55-14:20  //  15:55-16:55)
        // 05.01 - 3 часа 40 минут (10:30-11:30  //  12:10-13:30  //  14:00-14:25  //  15:20-16:15)
        //     ------- 61 час 40 минут
        // 07.01 -     (12:45-14:15  //
        //----------------------

        // =============================================================================

        // АПИ-ТЕСТЫ (без проверки) (Примерное время / количество)
        // КОММЕНТ:  28 сек / 7 тестов
        // ЛЕЙБЛ:    17 сек / 6 тестов
        // ПРОДЖЕКТ: 17 сек / 5 тестов
        // СЕКШЕН:   40 сек / 6 тестов (разброс от 28 до 52 сек)
        // ТАСК:     84 сек / 8 тестов (разброс от 71 до 97 сек)
        // --итого:  186 сек / 32 теста

        // =============================================================================



        RestAssured.baseURI = "https://api.todoist.com/rest/v2/";
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

    @AfterEach
    void cleanupTestData() {

        projectsApi.deleteProjects();
        labelsApi.deleteLabels();
    }
}
