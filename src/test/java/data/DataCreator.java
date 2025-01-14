package data;

import api.*;
import io.qameta.allure.Step;
import models.comments.CommentRequestModel;
import models.comments.CommentResponseModel;
import models.data.TestDataModel;
import models.labels.LabelRequestModel;
import models.projects.ProjectRequestModel;
import models.projects.ProjectResponseModel;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;

public class DataCreator {

    public static class Setup {

        private TestDataModel template;
        private boolean createLabels;
        private boolean createProjects;
        private boolean createCommentsInProjects;
        private boolean createTasksInProjects;
        private boolean addLabelsForTasksInProjects;
        private boolean createCommentsInTasksInProjects;
        private boolean createSections;
        private boolean createTasksInSections;
        private boolean addLabelsForTasksInSections;
        private boolean createCommentsInTasksInSections;

        public Setup setTemplate(TestDataModel template) {
            this.template = template;
            return this;
        }

        public Setup createLabels(boolean createLabels) {
            this.createLabels = createLabels;
            return this;
        }

        public Setup createProjects(boolean createProjects) {
            this.createProjects = createProjects;
            return this;
        }

        public Setup createCommentsInProjects(boolean createCommentsInProjects) {
            this.createCommentsInProjects = createCommentsInProjects;
            return this;
        }

        public Setup createTasksInProjects(boolean createTasksInProjects) {
            this.createTasksInProjects = createTasksInProjects;
            return this;
        }

        public Setup addLabelsForTasksInProjects(boolean addLabelsForTasksInProjects) {
            this.addLabelsForTasksInProjects = addLabelsForTasksInProjects;
            return this;
        }

        public Setup createCommentsInTasksInProjects(boolean createCommentsInTasksInProjects) {
            this.createCommentsInTasksInProjects = createCommentsInTasksInProjects;
            return this;
        }

        public Setup createSections(boolean createSections) {
            this.createSections = createSections;
            return this;
        }

        public Setup createTasksInSections(boolean createTasksInSections) {
            this.createTasksInSections = createTasksInSections;
            return this;
        }

        public Setup addLabelsForTasksInSections(boolean addLabelsForTasksInSections) {
            this.addLabelsForTasksInSections = addLabelsForTasksInSections;
            return this;
        }

        public Setup createCommentsInTasksInSections(boolean createCommentsInTasksInSections) {
            this.createCommentsInTasksInSections = createCommentsInTasksInSections;
            return this;
        }

        @Step("Подготовить данные для теста")
        public TestDataModel create() {

            DataCreator mainClass = new DataCreator();

            TestDataModel templateData = this.template;
            boolean createLabels = this.createLabels;
            boolean createProjects = this.createProjects;
            boolean createCommentsInProjects = this.createCommentsInProjects;
            boolean createTasksInProjects = this.createTasksInProjects;
            boolean addLabelsForTasksInProjects = this.addLabelsForTasksInProjects;
            boolean createCommentsInTasksInProjects = this.createCommentsInTasksInProjects;
            boolean createSections = this.createSections;
            boolean createTasksInSections = this.createTasksInSections;
            boolean addLabelsForTasksInSections = this.addLabelsForTasksInSections;
            boolean createCommentsInTasksInSections = this.createCommentsInTasksInSections;

            TestDataModel testData = new TestDataModel();

            if (createLabels) {
                mainClass.createLabels(testData, templateData);
            }

            if (createProjects) {
                mainClass.createProjects(testData, templateData);
            }

            if (createCommentsInProjects) {
                if (!createProjects) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь создать Комментарий в Проекте, но Проект не создан."
                }
                mainClass.createCommentsInProjects(testData, templateData);
            }

            if (createTasksInProjects) {
                if (!createProjects) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь создать Задачу в Проекте, но Проект не создан."
                }
                if (addLabelsForTasksInProjects && !createLabels) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь добавить Метку к Задаче в Проекте, но Метка не создана."
                }
                mainClass.createTasksInProjects(testData, templateData, addLabelsForTasksInProjects);
            }

            if (createCommentsInTasksInProjects) {
                if (!createProjects || !createTasksInProjects) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь создать Комментарий в Задаче в Проекте, но Задача не создана."
                }
                mainClass.createCommentsInTasksInProjects(testData, templateData);
            }

            if (createSections) {
                if (!createProjects) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь создать Раздел в Проекте, но Проект не создан."
                }
                mainClass.createSections(testData, templateData);
            }

            if (createTasksInSections) {
                if (!createProjects || !createSections) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь создать Задачу в Разделе в Проекте, но Раздел не создан."
                }
                if (addLabelsForTasksInSections && !createLabels) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь добавить Метку к Задаче в Разделе, но Метка не создана."
                }
                mainClass.createTasksInSections(testData, templateData, addLabelsForTasksInSections);
            }

            if (createCommentsInTasksInSections) {
                if (!createProjects || !createSections || !createTasksInSections) {
                    // TODO : выкинуть эксепшен, что "Вы пытаетесь создать Комментарий в Задаче в Разделе, но Задача не создана."
                }
                mainClass.createCommentsInTasksInSections(testData, templateData);
            }

            return testData;
        }
    }

    private static void createLabels(TestDataModel testData, TestDataModel templateData) {

        LabelsApi api = new LabelsApi();
        for (int i = 0; i < templateData.getLabels().size(); i++) {
            models.labels.LabelResponseModel label = templateData.getLabels().get(i);
            LabelRequestModel request = LabelRequestModel.builder()
                    .name(label.getName())
                    .color(label.getColor())
                    .isFavorite(label.isFavorite())
                    .build();

            label = api.createNewLabel(request);
            testData.getLabels().add(label);
        }
    }

    private static void createProjects(TestDataModel testData, TestDataModel templateData) {

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

    private static void createCommentsInProjects(TestDataModel testData, TestDataModel templateData) {

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

    private static void createTasksInProjects(TestDataModel testData, TestDataModel templateData, boolean addLabels) {

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

            if (addLabels) {
                builder.labels(task.getLabels());
            }

            TaskRequestModel request = builder.build();

            task = api.createNewTask(request);
            testData.getTasksInProjects().add(task);
        }
    }

    private static void createCommentsInTasksInProjects(TestDataModel testData, TestDataModel templateData) {

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

    private static void createSections(TestDataModel testData, TestDataModel templateData) {

        SectionsApi api = new SectionsApi();
        for (int i = 0; i < templateData.getSections().size(); i++) {
            SectionResponseModel section = templateData.getSections().get(i);
            int projectNumber = Integer.parseInt(section.getProjectId());
            String projectId = testData.getProjects().get(projectNumber).getId();
            SectionRequestModel request = SectionRequestModel.builder()
                    .projectId(projectId)
                    .name(section.getName())
                    .order(section.getOrder())
                    .build();

            section = api.createNewSection(request);
            testData.getSections().add(section);
        }
    }

    private static void createTasksInSections(TestDataModel testData, TestDataModel templateData, boolean addLabels) {

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

            if (addLabels) {
                builder.labels(task.getLabels());
            }

            TaskRequestModel request = builder.build();

            task = api.createNewTask(request);
            testData.getTasksInSections().add(task);
        }
    }

    private static void createCommentsInTasksInSections(TestDataModel testData, TestDataModel templateData) {

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