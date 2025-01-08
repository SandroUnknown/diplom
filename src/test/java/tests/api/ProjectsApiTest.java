package tests.api;

import data.DataCreator;
import models.data.TestDataModel;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static enums.Color.*;
import static enums.ViewStyle.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : может быть тест переменные передавать как параметры?
// TODO : дописать теги, овнера и прочие данные

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через API")
@Feature("Проверка проектов через API")
@Tags({ @Tag("API"), @Tag("project") })
@DisplayName("Проверка проектов через API")
public class ProjectsApiTest extends ApiTestBase {
        
    private final ProjectRequestModel testProjectData = ProjectRequestModel.builder()
                .name("НОВЫЙ ПРОЕКТ")
                .color(YELLOW)
                .viewStyle(BOARD)
                .build();

    private final ProjectRequestModel testInnerProjectData = ProjectRequestModel.builder()
            .name("НОВЫЙ вложенный ПРОЕКТ")
            .color(GREEN)
            .viewStyle(BOARD)
            .build();

    private final ProjectRequestModel updatedTestProjectData = ProjectRequestModel.builder()
                .name("ОБНОВЛЁННЫЙ ПРОЕКТ")
                .color(RED)
                .isFavorite(true)
                .viewStyle(LIST)
                .build();
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового проекта (с заполнением имени, цвета и варианта отображения)")
    @DisplayName("Создать новый проект (с заполнением имени, цвета и варианта отображения)")
    void createNewProjectTest() {

        ProjectResponseModel myCreatedProject = projectsApi.createNewProject(testProjectData);

        step("Проверить, что проект был корректно создан", () -> {
                assertThat(myCreatedProject.getName()).isEqualTo(testProjectData.getName());
                assertThat(myCreatedProject.getColor()).isEqualTo(testProjectData.getColor());
                assertThat(myCreatedProject.getViewStyle()).isEqualTo(testProjectData.getViewStyle());
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового вложенного проекта (с заполнением имени, цвета и варианта отображения)")
    @DisplayName("Создать новый вложенный проект (с заполнением имени, цвета и варианта отображения)")
    void createNewProjectInProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        String parentProjectId = testData.getProjects().get(0).getId();
        testInnerProjectData.setParentId(parentProjectId);

        ProjectResponseModel myCreatedInnerProject = projectsApi.createNewProject(testInnerProjectData);

        step("Проверить, что проект был корректно создан", () -> {
                assertThat(myCreatedInnerProject.getName()).isEqualTo(testInnerProjectData.getName());
                assertThat(myCreatedInnerProject.getColor()).isEqualTo(testInnerProjectData.getColor());
                assertThat(myCreatedInnerProject.getViewStyle()).isEqualTo(testInnerProjectData.getViewStyle());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Обновление проекта по ID")
    @DisplayName("Обновить проект по ID")
    void updateProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();

        ProjectResponseModel myCreatedProject = projectsApi.updateProject(projectId, updatedTestProjectData);

        step("Проверить, что проект был корректно обновлен", () -> {
                assertThat(myCreatedProject.getName()).isEqualTo(updatedTestProjectData.getName());
                assertThat(myCreatedProject.getColor()).isEqualTo(updatedTestProjectData.getColor());
                assertThat(myCreatedProject.getViewStyle()).isEqualTo(updatedTestProjectData.getViewStyle());
                assertThat(myCreatedProject.isFavorite()).isEqualTo(updatedTestProjectData.isFavorite());
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление проекта по ID")
    @DisplayName("Удалить проект по ID")
    void deleteProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();

        projectsApi.deleteProject(projectId);
        ProjectResponseModel myDeletedProject = projectsApi.getProject(projectId);

        step("Проверить, что проект действительно был удалён", () -> {
                assertThat(myDeletedProject.getName()).isEqualTo("");
                assertThat(myDeletedProject.getOrder()).isEqualTo("0");
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление всех проектов пользователя")
    @DisplayName("Удалить все проекты пользователя")
    void deleteAllProjectsTest() {

        int templateNumber = 1;

        new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        projectsApi.deleteProjects();

        step("Проверить, все проекты пользователя действительно были удалены", () -> {
                int currentProjectCount = projectsApi.getAllProjects().size();
                assertThat(currentProjectCount).isEqualTo(1);
        });
    }
}
