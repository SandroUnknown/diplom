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

    static final List<TestDataModel> TEMPLATES = new DataStorage("data/Templates.json").getTemplates();

    final ProjectsApi projectsApi = new ProjectsApi();
    final SectionsApi sectionsApi = new SectionsApi();
    final TasksApi tasksApi = new TasksApi();
    final CommentsApi commentsApi = new CommentsApi();
    final LabelsApi labelsApi = new LabelsApi();

    @BeforeAll
    public static void setUp() {
        new ApiConfigDriver();
    }

    @BeforeEach
    void preTest() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}