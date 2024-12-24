package helpers.annotations;

import api.LabelsApi;
import api.ProjectsApi;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CleanupTestDataExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {

        ProjectsApi projectsApi = new ProjectsApi();
        projectsApi.deleteProjects();

        LabelsApi labelsApi = new LabelsApi();
        labelsApi.deleteLabels();
    }
}