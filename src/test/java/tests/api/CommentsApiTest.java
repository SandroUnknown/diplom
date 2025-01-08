package tests.api;

import data.DataCreator;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;
import models.data.TestDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO : может быть тест переменные передавать как параметры?

@Owner("Petyukov Alexander")
@Epic("Проверка рабочего пространства пользователя через API")
@Feature("Проверка комментариев через API")
@Tags({ @Tag("API"), @Tag("comment") })
@DisplayName("Проверка комментариев через API")
public class CommentsApiTest extends ApiTestBase {

    private final CommentRequestModel testCommentData = CommentRequestModel.builder()
            .content("НОВЫЙ КОММЕНТАРИЙ")
            .build();

    private final CommentRequestModel updatedTestCommentData = CommentRequestModel.builder()
            .content("ОБНОВЛЁННЫЙ КОММЕНТАРИЙ")
            .build();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового комментария в проекте")
    @DisplayName("Создать новый комментарий в проекте")
    void createNewCommentInProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .create();

        String projectId = testData.getProjects().get(0).getId();

        CommentResponseModel myCreatedComment =
                commentsApi.createNewCommentInProject(projectId, testCommentData.getContent());

        step("Проверить, что комментарий был корректно создан", () -> {
            assertThat(myCreatedComment.getContent()).isEqualTo(testCommentData.getContent());
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Создание нового комментария в задаче")
    @DisplayName("Создать новый комментарий в задаче")
    void createNewCommentInTaskTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();

        CommentResponseModel myCreatedComment =
                commentsApi.createNewCommentInTask(taskId, testCommentData.getContent());
            
        step("Проверить, что комментарий был корректно создан", () -> {
            assertThat(myCreatedComment.getContent()).isEqualTo(testCommentData.getContent());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Обновление комментария по ID")
    @DisplayName("Обновить комментарий по ID")
    void updateCommentTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        CommentResponseModel myUpdatedComment =
                commentsApi.updateComment(commentId, updatedTestCommentData.getContent());

        step("Проверить, что комментарий был корректно обновлён", () -> {
            assertThat(myUpdatedComment.getContent()).isEqualTo(updatedTestCommentData.getContent());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение комментария по ID")
    @DisplayName("Получить комментарий по ID")
    void getCommentTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        CommentResponseModel myCreatedComment = testData.getCommentsInTasksInSections().get(0);
        String commentId = testData.getCommentsInTasksInSections().get(0).getId();

        CommentResponseModel myReceivedComment = commentsApi.getComment(commentId);

        step("Проверить, что комментарий был корректно получен", () -> {
            assertThat(myReceivedComment.getContent()).isEqualTo(myCreatedComment.getContent());
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех комментариев в проекте")
    @DisplayName("Получить все комментарии в проекте")
    void getAllCommentsInProjectTest() {

        int templateNumber = 0;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createCommentsInProjects(true)
                .create();

        List<CommentResponseModel> myCreatedComments = testData.getCommentsInProjects();
        String projectId = testData.getProjects().get(0).getId();

        List<CommentResponseModel> myReceivedComments = commentsApi.getAllCommentsInProject(projectId);

        step("Проверить, что комментарии были корректно получены", () -> {
            assertThat(myReceivedComments.size()).isEqualTo(myCreatedComments.size());
            for(int i = 0; i < myCreatedComments.size(); i++) {
                assertThat(myReceivedComments.get(i).getContent()).isEqualTo(myCreatedComments.get(i).getContent());
            }
        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Получение всех комментариев в задаче")
    @DisplayName("Получить все комментарии в задаче")
    void getAllCommentsInTaskTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        String taskId = testData.getTasksInSections().get(0).getId();

        List<CommentResponseModel> myCreatedComments = new ArrayList<>();
        for(CommentResponseModel myCreatedComment : testData.getCommentsInTasksInSections()) {
            if (myCreatedComment.getTaskId().equals(taskId)) {
                myCreatedComments.add(myCreatedComment);
            }
        }

        List<CommentResponseModel> myReceivedComments = commentsApi.getAllCommentsInTask(taskId);

        step("Проверить, что комментарии были корректно получены", () -> {
            assertThat(myReceivedComments.size()).isEqualTo(myCreatedComments.size());
            for(int i = 0; i < myCreatedComments.size(); i++) {
                assertThat(myReceivedComments.get(i).getContent()).isEqualTo(myCreatedComments.get(i).getContent());
            }
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Удаление комментария по ID")
    @DisplayName("Удалить комментарий по ID")
    void deleteCommentTest() {

        int templateNumber = 1;

        TestDataModel testData = new DataCreator.Setup()
                .setTemplate(TEMPLATES.get(templateNumber))
                .createProjects(true)
                .createSections(true)
                .createTasksInSections(true)
                .createCommentsInTasksInSections(true)
                .create();

        String commentId = testData.getCommentsInTasksInSections().get(0).getId();
        String taskId = testData.getCommentsInTasksInSections().get(0).getTaskId();

        List<CommentResponseModel> myCreatedComments = new ArrayList<>();
        for(CommentResponseModel myCreatedComment : testData.getCommentsInTasksInSections()) {
            if (myCreatedComment.getTaskId().equals(taskId)) {
                myCreatedComments.add(myCreatedComment);
            }
        }
        int createdCommentCount = myCreatedComments.size();

        commentsApi.deleteComment(commentId);

        step("Проверить, что комментарий действительно был удалён", () -> {
            int currentCommentCount = commentsApi.getAllCommentsInTask(taskId).size();
            assertThat(currentCommentCount).isEqualTo(createdCommentCount - 1);
        });
    }
}
