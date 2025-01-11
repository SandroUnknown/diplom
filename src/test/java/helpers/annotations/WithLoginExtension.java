package helpers.annotations;

import api.*;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WithLoginExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {

        ProjectsApi projectsApi = new ProjectsApi();
        LabelsApi labelsApi = new LabelsApi();

        projectsApi.deleteProjects();
        labelsApi.deleteLabels();
    }
}