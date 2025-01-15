package helpers.annotations;

import api.LabelsApi;
import api.ProjectsApi;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CleanupTestDataExtension implements AfterEachCallback {

    @Override
    @Step("Удалить все тестовые данные")
    public void afterEach(ExtensionContext context) {

        new ProjectsApi().deleteProjects();
        new LabelsApi().deleteLabels();
    }
}