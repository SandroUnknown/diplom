package tests.api;

import api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataStorage;
import drivers.ApiConfigDriver;
import io.qameta.allure.selenide.AllureSelenide;
import models.data.TestDataModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class ApiTestBase {

    static List<TestDataModel> TEMPLATES = new DataStorage("data/Templates_TWO.json").getTemplates();

    ProjectsApi projectsApi = new ProjectsApi();
    SectionsApi sectionsApi = new SectionsApi();
    TasksApi tasksApi = new TasksApi();
    CommentsApi commentsApi = new CommentsApi();
    LabelsApi labelsApi = new LabelsApi();

    @BeforeAll
    public static void setUp() {
        new ApiConfigDriver();
    }

    @BeforeEach
    void preTest() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}