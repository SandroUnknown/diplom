package tests.web;

import api.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataStorage;
import drivers.ApiConfigDriver;
import drivers.BrowserstackDriver;
import drivers.WebConfigDriver;
import helpers.attachments.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.data.TestDataModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.AuthPage;
import pages.ProjectPage;
import pages.SectionPage;
import pages.TaskPage;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class WebTestBase {

    static List<TestDataModel> TEMPLATES;

    // АПИ
    ProjectsApi projectsApi = new ProjectsApi();
    SectionsApi sectionsApi = new SectionsApi();
    TasksApi tasksApi = new TasksApi();
    CommentsApi commentsApi = new CommentsApi();
    LabelsApi labelsApi = new LabelsApi();

    // Пейдж
    ProjectPage projectPage = new ProjectPage();
    SectionPage sectionPage = new SectionPage();
    TaskPage taskPage = new TaskPage();
    AuthPage authPage = new AuthPage();

    @BeforeAll
    public static void setUp() {

        // TODO : хардкодить адрес с данными? === "data/ProjectTemplates2.json"
        TEMPLATES = new DataStorage("data/ProjectTemplates2.json").getTemplates();

        new ApiConfigDriver();
        new WebConfigDriver();
    }

    @BeforeEach
    void preTest() {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {

        Attach.screenshotAs("Last screenshot");
        if (!Configuration.browser.equals("firefox")) {
            Attach.pageSource();
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();
        closeWebDriver();
    }

    @AfterEach
    void cleanupTestData() {

        /*projectsApi.deleteProjects();
        labelsApi.deleteLabels();*/
    }
}