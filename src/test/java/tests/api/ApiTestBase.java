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

public class ApiTestBase {

    static List<TestDataModel> TEMPLATES;

    ProjectsApi projectsApi = new ProjectsApi();
    SectionsApi sectionsApi = new SectionsApi();
    TasksApi tasksApi = new TasksApi();
    CommentsApi commentsApi = new CommentsApi();
    LabelsApi labelsApi = new LabelsApi();

    @BeforeAll
    public static void setUp() {

        // TODO : хардкодить адрес с данными? === "data/Templates.json"
        TEMPLATES = new DataStorage("data/Templates.json").getTemplates();

        new ApiConfigDriver();
    }

    @BeforeEach
    void preTest() {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    /*@AfterEach
    void cleanupTestData() {

        projectsApi.deleteProjects();
        labelsApi.deleteLabels();
    }*/
}