package helpers.data;

import api.*;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.qameta.allure.internal.shadowed.jackson.databind.PropertyNamingStrategy;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;
import models.data.TestData;
import models.data.TestDataConfig;
import models.labels.LabelRequestModel;
import models.labels.LabelResponseModel;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class DataCreator {

    // TODO : передавать имя файла в конструкторе?
    private static final List<TestData> PROJECT_TEMPLATES = getProjectTemplatesFromFile("data/ProjectTemplates.json");

    private static List<TestData> getProjectTemplatesFromFile(String fileName) {

        ClassLoader cl = DataCreator.class.getClassLoader();
        ObjectMapper om = new ObjectMapper();

        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        try (Reader reader = new InputStreamReader(cl.getResourceAsStream(fileName))) {
            return Arrays.asList(om.readValue(reader, TestData[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TestData create(int templateId, TestDataConfig whatIsCreate) {

        TestData templateData = PROJECT_TEMPLATES.get(templateId);
        TestData testData = new TestData();

        createLabels(testData, templateData, whatIsCreate);
        createProjects(testData, templateData, whatIsCreate);
        createCommentsInProjects(testData, templateData, whatIsCreate);
        createTasksInProjects(testData, templateData, whatIsCreate);
        createCommentsInTasksInProjects(testData, templateData, whatIsCreate);
        createSections(testData, templateData, whatIsCreate);
        createTasksInSections(testData, templateData, whatIsCreate);
        createCommentsInTasksInSections(testData, templateData, whatIsCreate);

        return testData;
    }


    // TODO : сократить все create-методы до одного?...
    private void createLabels(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateLabels()) {

            LabelsApi api = new LabelsApi();
            for (int i = 0; i < templateData.getLabels().size(); i++) {
                LabelResponseModel label = templateData.getLabels().get(i);
                LabelRequestModel request = LabelRequestModel.builder()
                        .name(label.getName())
                        .color(label.getColor())
                        .isFavorite(label.isFavorite())
                        .build();

                label = api.createNewLabel(request);
                testData.getLabels().add(label);
            }
        }
    }

    private void createProjects(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()) {

            ProjectsApi api = new ProjectsApi();
            for (int i = 0; i < templateData.getProjects().size(); i++) {
                ProjectResponseModel project = templateData.getProjects().get(i);
                ProjectRequestModel request = ProjectRequestModel.builder()
                        .name(project.getName())
                        .color(project.getColor())
                        .viewStyle(project.getViewStyle())
                        .build();

                project = api.createNewProject(request);
                testData.getProjects().add(project);
            }
        }
    }

    private void createCommentsInProjects(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()
                && whatIsCreate.isCreateCommentsInProjects()) {

            CommentsApi api = new CommentsApi();
            for (int i = 0; i < templateData.getCommentsInProjects().size(); i++) {
                CommentResponseModel comment = templateData.getCommentsInProjects().get(i);
                int projectNumber = Integer.parseInt(comment.getProjectId());
                String projectId = testData.getProjects().get(projectNumber).getId();
                CommentRequestModel request = CommentRequestModel.builder()
                        .projectId(projectId)
                        .content(comment.getContent())
                        .build();

                comment = api.createNewComment(request);
                testData.getCommentsInProjects().add(comment);
            }
        }
    }

    private void createTasksInProjects(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()
                && whatIsCreate.isCreateTasksInProjects()) {

            TasksApi api = new TasksApi();
            for (int i = 0; i < templateData.getTasksInProjects().size(); i++) {
                TaskResponseModel task = templateData.getTasksInProjects().get(i);
                int projectNumber = Integer.parseInt(task.getProjectId());
                String projectId = testData.getProjects().get(projectNumber).getId();

                TaskRequestModel.TaskRequestModelBuilder builder = TaskRequestModel.builder()
                        .projectId(projectId)
                        .content(task.getContent())
                        //.order(task.getOrder()) // TODO : нужно?
                        .priority(task.getPriority());

                if (whatIsCreate.isCreateLabels()
                        && whatIsCreate.isAddLabelsForTasksInProjects()) {
                    builder.labels(task.getLabels());
                }

                TaskRequestModel request = builder.build();

                task = api.createNewTask(request);
                testData.getTasksInProjects().add(task);
            }
        }
    }

    private void createCommentsInTasksInProjects(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()
                && whatIsCreate.isCreateTasksInProjects()
                && whatIsCreate.isCreateCommentsInTasksInProjects()) {

            CommentsApi api = new CommentsApi();
            for (int i = 0; i < templateData.getCommentsInTasksInProjects().size(); i++) {
                CommentResponseModel comment = templateData.getCommentsInTasksInProjects().get(i);
                int taskNumber = Integer.parseInt(comment.getTaskId());
                String taskId = testData.getTasksInProjects().get(taskNumber).getId();
                CommentRequestModel request = CommentRequestModel.builder()
                        .taskId(taskId)
                        .content(comment.getContent())        // TODO : заменить на поля комментов
                        .build();

                comment = api.createNewComment(request);
                testData.getCommentsInTasksInProjects().add(comment);
            }
        }
    }

    private void createSections(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()
                && whatIsCreate.isCreateSections()) {

            SectionsApi api = new SectionsApi();
            for (int i = 0; i < templateData.getSections().size(); i++) {
                SectionResponseModel section = templateData.getSections().get(i);
                int projectNumber = Integer.parseInt(section.getProjectId());
                String projectId = testData.getProjects().get(projectNumber).getId();
                SectionRequestModel request = SectionRequestModel.builder()
                        .projectId(projectId)
                        .name(section.getName())
                        .build();

                section = api.createNewSection(request);
                testData.getSections().add(section);
            }
        }
    }

    private void createTasksInSections(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()
                && whatIsCreate.isCreateSections()
                && whatIsCreate.isCreateTasksInSections()) {

            TasksApi api = new TasksApi();
            for (int i = 0; i < templateData.getTasksInSections().size(); i++) {
                TaskResponseModel task = templateData.getTasksInSections().get(i);
                int sectionNumber = Integer.parseInt(task.getSectionId());
                String sectionId = testData.getSections().get(sectionNumber).getId();

                TaskRequestModel.TaskRequestModelBuilder builder = TaskRequestModel.builder()
                        .sectionId(sectionId)
                        .content(task.getContent())
                        //.order(task.getOrder()) // TODO : нужно?
                        .priority(task.getPriority());

                if (whatIsCreate.isCreateLabels()
                        && whatIsCreate.isAddLabelsForTasksInSections()) {
                    builder.labels(task.getLabels());
                }

                TaskRequestModel request = builder.build();

                task = api.createNewTask(request);
                testData.getTasksInSections().add(task);
            }
        }
    }

    private void createCommentsInTasksInSections(TestData testData, TestData templateData, TestDataConfig whatIsCreate) {

        if (whatIsCreate.isCreateProjects()
                && whatIsCreate.isCreateSections()
                && whatIsCreate.isCreateTasksInSections()
                && whatIsCreate.isCreateCommentsInTasksInSections()) {

            CommentsApi api = new CommentsApi();
            for (int i = 0; i < templateData.getCommentsInTasksInSections().size(); i++) {
                CommentResponseModel comment = templateData.getCommentsInTasksInSections().get(i);
                int taskNumber = Integer.parseInt(comment.getTaskId());
                String taskId = testData.getTasksInSections().get(taskNumber).getId();
                CommentRequestModel request = CommentRequestModel.builder()
                        .taskId(taskId)
                        .content(comment.getContent())
                        .build();

                comment = api.createNewComment(request);
                testData.getCommentsInTasksInSections().add(comment);
            }
        }
    }
}
