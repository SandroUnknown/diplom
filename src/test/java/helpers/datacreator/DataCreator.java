package helpers.datacreator;

import api.*;
import com.opencsv.CSVReader;
import enums.Color;
import models.comments.CommentRequestModel;
import models.labels.LabelRequestModel;
import models.projects.ProjectRequestModel;
import models.sections.SectionRequestModel;
import models.tasks.TaskRequestModel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static enums.ViewStyle.BOARD;

public class DataCreator {

    public List<String> createLabels(int minLabelsCount, int maxLabelsCount) {

        LabelsApi api = new LabelsApi();
        List<String> labelsId = new ArrayList<>();
        List<String[]> data = openFile("data/labelsList.csv");

        Random randomLabelsCount = new Random();
        int labelsCount = randomLabelsCount.nextInt(
                maxLabelsCount - minLabelsCount + 1) + minLabelsCount;
        labelsCount = Math.min(labelsCount, data.size());

        for (int i = 1; i <= labelsCount; i++) {

            Random randomNamesIndex = new Random();
            int nameIndex = randomNamesIndex.nextInt(data.size());
            String labelName = data.get(nameIndex)[0];
            data.remove(nameIndex);

            Color[] colors = Color.values();
            Random randomColors = new Random();
            Color color = colors[randomColors.nextInt(colors.length)];

            LabelRequestModel model = LabelRequestModel.builder()
                    .name(labelName)
                    .color(color)
                    .isFavorite(true)
                    .build();

            //labelsId.add(api.createNewLabel(model).getName());
            labelsId.add(api.createNewLabel(model).getId());
        }

        return labelsId;
    }

    public List<String> createLabels(int labelsCount) {

        return createLabels(labelsCount, labelsCount);
    }

    public String createLabel() {

        return createLabels(1, 1).get(0);
    }


    public List<String> createProjects(int minProjectsCount, int maxProjectsCount) {

        ProjectsApi api = new ProjectsApi();
        List<String> projectsId = new ArrayList<>();
        List<String[]> data = openFile("data/projectsList.csv");

        Random randomProjectsCount = new Random();
        int projectsCount = randomProjectsCount.nextInt(
                maxProjectsCount - minProjectsCount + 1) + minProjectsCount;
        projectsCount = Math.min(projectsCount, data.size());

        for (int i = 1; i <= projectsCount; i++) {

            Random randomNamesIndex = new Random();
            int nameIndex = randomNamesIndex.nextInt(data.size());
            String projectName = data.get(nameIndex)[0];
            data.remove(nameIndex);

            Color[] colors = Color.values();
            Random randomColors = new Random();
            Color color = colors[randomColors.nextInt(colors.length)];

            ProjectRequestModel model = ProjectRequestModel.builder()
                    .name(projectName)
                    .color(color)
                    .viewStyle(BOARD)
                    .build();

            projectsId.add(api.createNewProject(model).getId());
        }

        return projectsId;
    }

    public List<String> createProjects(int projectsCount) {

        return createProjects(projectsCount, projectsCount);
    }

    public String createProject() {

        return createProjects(1, 1).get(0);
    }


    public List<String> createSections(List<String> projectsId, int minSectionsCountInEachProject, int maxSectionsCountInEachProject) {

        SectionsApi api = new SectionsApi();
        List<String> sectionsId = new ArrayList<>();
        List<String[]> data = openFile("data/sectionsList.csv");

        int projectsCount = projectsId.size();

        for (int j = 0; j < projectsCount; j++) {
            Random randomSectionsCount = new Random();
            int sectionsCount = randomSectionsCount.nextInt(
                    maxSectionsCountInEachProject - minSectionsCountInEachProject + 1)
                    + minSectionsCountInEachProject;
            sectionsCount = Math.min(sectionsCount, data.size());

            for (int i = 1; i <= sectionsCount; i++) {

                Random randomNamesIndex = new Random();
                int nameIndex = randomNamesIndex.nextInt(data.size());
                String sectionName = data.get(nameIndex)[0];
                data.remove(nameIndex);

                SectionRequestModel model = SectionRequestModel.builder()
                        .name(sectionName)
                        .projectId(projectsId.get(j))
                        .build();

                sectionsId.add(api.createNewSection(model).getId());
            }
        }

        return sectionsId;
    }

    public List<String> createSections(List<String> projectsId, int sectionsCountInEachProject) {

        return createSections(projectsId, sectionsCountInEachProject, sectionsCountInEachProject);
    }

    public List<String> createSections(String projectId, int minSectionsCountInProject, int maxSectionsCountInProject) {

        List<String> projectsId = new ArrayList<>();
        projectsId.add(projectId);

        return createSections(projectsId, minSectionsCountInProject, maxSectionsCountInProject);
    }

    public List<String> createSections(String projectId, int sectionsCountInProject) {

        List<String> projectsId = new ArrayList<>();
        projectsId.add(projectId);

        return createSections(projectsId, sectionsCountInProject, sectionsCountInProject);
    }

    public String createSection(String projectId) {

        List<String> projectsId = new ArrayList<>();
        projectsId.add(projectId);

        return createSections(projectsId, 1, 1).get(0);
    }


    public List<String> createTasks(List<String> sectionsId, int minTasksCountInEachSection, int maxTasksCountInEachSection, List<String> labelsName) {

        TasksApi api = new TasksApi();
        List<String> tasksId = new ArrayList<>();
        List<String[]> data = openFile("data/tasksList.csv");

        int sectionsCount = sectionsId.size();

        for (int j = 0; j < sectionsCount; j++) {
            List<String[]> taskData = new ArrayList<>(data);

            Random randomTasksCount = new Random();
            int tasksCount = randomTasksCount.nextInt(
                    maxTasksCountInEachSection - minTasksCountInEachSection + 1)
                    + minTasksCountInEachSection;
            tasksCount = Math.min(tasksCount, taskData.size());

            for (int i = 1; i <= tasksCount; i++) {

                Random randomContentsIndex = new Random();
                int contentIndex = randomContentsIndex.nextInt(taskData.size());
                String taskContent = taskData.get(contentIndex)[0];
                taskData.remove(contentIndex);

                Random randomPriority = new Random();
                int priority = randomPriority.nextInt(4) + 1;

                Random randomLabelsCount = new Random();
                int labelsCount = randomLabelsCount.nextInt(3);
                labelsCount = Math.min(labelsCount, labelsName.size());

                List<String> labels = new ArrayList<>();
                for (int k = 1; k <= labelsCount; k++) {
                    Random randomLabelsIndex = new Random();
                    int labelsIndex = randomLabelsIndex.nextInt(labelsName.size());
                    labels.add(labelsName.get(labelsIndex));
                }

                TaskRequestModel model = TaskRequestModel.builder()
                        .content(taskContent)
                        .priority(priority)
                        .sectionId(sectionsId.get(j))
                        .labels(labels)
                        .build();

                tasksId.add(api.createNewTask(model).getId());
            }
        }

        return tasksId;
    }

    public List<String> createTasks(List<String> sectionsId, int minTasksCountInEachSection, int maxTasksCountInEachSection, String labelName) {

        List<String> labeslName = new ArrayList<>();
        labeslName.add(labelName);

        return createTasks(sectionsId, minTasksCountInEachSection, maxTasksCountInEachSection, labeslName);
    }

    public List<String> createTasks(List<String> sectionsId, int minTasksCountInEachSection, int maxTasksCountInEachSection) {

        return createTasks(sectionsId, minTasksCountInEachSection, maxTasksCountInEachSection, new ArrayList<>());
    }

    public List<String> createTasks(List<String> sectionsId, int tasksCountInEachSection) {

        return createTasks(sectionsId, tasksCountInEachSection, tasksCountInEachSection);
    }

    public List<String> createTasks(String sectionId, int minTasksCountInSection, int maxTasksCountInSection) {

        List<String> sectionsId = new ArrayList<>();
        sectionsId.add(sectionId);

        return createTasks(sectionsId, minTasksCountInSection, maxTasksCountInSection);
    }

    public List<String> createTasks(String sectionId, int tasksCountInSection) {

        List<String> sectionsId = new ArrayList<>();
        sectionsId.add(sectionId);

        return createTasks(sectionsId, tasksCountInSection, tasksCountInSection);
    }

    public String createTask(String sectionId) {

        List<String> sectionsId = new ArrayList<>();
        sectionsId.add(sectionId);

        return createTasks(sectionsId, 1, 1).get(0);
    }


    public List<String> createCommentsInTask(List<String> tasksId, int minCommentsCountInEachTask, int maxCommentsCountInEachTask) {

        CommentsApi api = new CommentsApi();
        List<String> commentsId = new ArrayList<>();
        List<String[]> data = openFile("data/commentsList.csv");

        int tasksCount = tasksId.size();

        for (int j = 0; j < tasksCount; j++) {
            Random randomCommentsCount = new Random();
            int commentsCount = randomCommentsCount.nextInt(
                    maxCommentsCountInEachTask - minCommentsCountInEachTask + 1)
                    + minCommentsCountInEachTask;

            for (int i = 1; i <= commentsCount; i++) {

                Random randomContentsIndex = new Random();
                int contentIndex = randomContentsIndex.nextInt(data.size());
                String commentContent = data.get(contentIndex)[0];

                CommentRequestModel model = CommentRequestModel.builder()
                        .content(commentContent)
                        .taskId(tasksId.get(j))
                        .build();

                commentsId.add(api.createNewComment(model).getId());
            }
        }

        return commentsId;
    }

    public List<String> createCommentsInTask(List<String> tasksId, int commentsCountInEachTask) {

        return createCommentsInTask(tasksId, commentsCountInEachTask, commentsCountInEachTask);
    }

    public List<String> createCommentsInTask(String taskId, int minCommentsCountInTask, int maxCommentsCountInTask) {

        List<String> tasksId = new ArrayList<>();
        tasksId.add(taskId);

        return createCommentsInTask(tasksId, minCommentsCountInTask, maxCommentsCountInTask);
    }

    public List<String> createCommentsInTask(String taskId, int commentsCountInTask) {

        List<String> tasksId = new ArrayList<>();
        tasksId.add(taskId);

        return createCommentsInTask(tasksId, commentsCountInTask, commentsCountInTask);
    }

    public String createCommentInTask(String taskId) {

        List<String> tasksId = new ArrayList<>();
        tasksId.add(taskId);

        return createCommentsInTask(tasksId, 1, 1).get(0);
    }


    public List<String> createCommentsInProject(List<String> projectsId, int minCommentsCountInEachProject, int maxCommentsCountInEachProject) {

        CommentsApi api = new CommentsApi();
        List<String> commentsId = new ArrayList<>();
        List<String[]> data = openFile("data/commentsList.csv");

        int projectsCount = projectsId.size();

        for (int j = 0; j < projectsCount; j++) {
            Random randomCommentsCount = new Random();
            int commentsCount = randomCommentsCount.nextInt(
                    maxCommentsCountInEachProject - minCommentsCountInEachProject + 1)
                    + minCommentsCountInEachProject;

            for (int i = 1; i <= commentsCount; i++) {

                Random randomContentsIndex = new Random();
                int contentIndex = randomContentsIndex.nextInt(data.size());
                String commentContent = data.get(contentIndex)[0];

                CommentRequestModel model = CommentRequestModel.builder()
                        .content(commentContent)
                        .projectId(projectsId.get(j))
                        .build();

                commentsId.add(api.createNewComment(model).getId());
            }
        }

        return commentsId;
    }

    public List<String> createCommentsInProject(List<String> projectsId, int commentsCountInEachProject) {

        return createCommentsInProject(projectsId, commentsCountInEachProject, commentsCountInEachProject);
    }

    public List<String> createCommentsInProject(String projectId, int minCommentsCountInProject, int maxCommentsCountInProject) {

        List<String> projectsId = new ArrayList<>();
        projectsId.add(projectId);

        return createCommentsInProject(projectsId, minCommentsCountInProject, maxCommentsCountInProject);
    }

    public List<String> createCommentsInProject(String projectId, int commentsCountInProject) {

        List<String> projectsId = new ArrayList<>();
        projectsId.add(projectId);

        return createCommentsInProject(projectsId, commentsCountInProject, commentsCountInProject);
    }

    public String createCommentInProject(String projectId) {

        List<String> projectsId = new ArrayList<>();
        projectsId.add(projectId);

        return createCommentsInProject(projectsId, 1, 1).get(0);
    }


    private List<String[]> openFile(String fileName) {

        ClassLoader cl = DataCreator.class.getClassLoader();

        try (InputStream is = cl.getResourceAsStream(fileName);
             CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
            return csvReader.readAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}