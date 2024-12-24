package helpers.datacreator;

import api.CommentsApi;
import api.ProjectsApi;
import api.SectionsApi;
import api.TasksApi;
import com.opencsv.CSVReader;
import enums.Color;
import models.comments.CommentRequestModel;
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




    // =================================================


    public List<String> createProjects(int minProjectsCount, int maxProjectsCount) {

        ProjectsApi api = new ProjectsApi();
        List<String> projectsId = new ArrayList<>();
        List<String[]> data = openFile("data/projectsList.csv");

        Random randomProjectsCount = new Random();
        int projectsCount = randomProjectsCount.nextInt(maxProjectsCount - minProjectsCount + 1) + minProjectsCount;
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


    public List<String> createTasks(List<String> sectionsId, int minTasksCountInEachSection, int maxTasksCountInEachSection) {

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

                // TODO : добавлять ещё метки?
                TaskRequestModel model = TaskRequestModel.builder()
                        .content(taskContent)
                        .priority(priority)
                        .sectionId(sectionsId.get(j))
                        .build();

                tasksId.add(api.createNewTask(model).getId());
            }
        }

        return tasksId;
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


    public List<String> createComments(List<String> tasksId, int minCommentsCountInEachTask, int maxCommentsCountInEachTask) {

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

    public List<String> createComments(List<String> tasksId, int commentsCountInEachTask) {

        return createComments(tasksId, commentsCountInEachTask, commentsCountInEachTask);
    }

    public List<String> createComments(String taskId, int minCommentsCountInTask, int maxCommentsCountInTask) {

        List<String> tasksId = new ArrayList<>();
        tasksId.add(taskId);

        return createComments(tasksId, minCommentsCountInTask, maxCommentsCountInTask);
    }

    public List<String> createComments(String taskId, int commentsCountInTask) {

        List<String> tasksId = new ArrayList<>();
        tasksId.add(taskId);

        return createComments(tasksId, commentsCountInTask, commentsCountInTask);
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