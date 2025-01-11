package tests.api;

import api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataStorage;
import drivers.ApiConfigDriver;
import io.qameta.allure.selenide.AllureSelenide;
import models.data.TestDataModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ApiTestBase {

    static List<TestDataModel> TEMPLATES;

    ProjectsApi projectsApi = new ProjectsApi();
    SectionsApi sectionsApi = new SectionsApi();
    TasksApi tasksApi = new TasksApi();
    CommentsApi commentsApi = new CommentsApi();
    LabelsApi labelsApi = new LabelsApi();

    @BeforeAll
    public static void setUp() {

        // TODO : хардкодить адрес с данными? === "data/ProjectTemplates2.json"
        TEMPLATES = new DataStorage("data/ProjectTemplates2.json").getTemplates();

        new ApiConfigDriver();
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