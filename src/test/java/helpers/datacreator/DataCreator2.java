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

public class DataCreator2 {

// === МЕТКИ =================================================
  
  public List<LabelRequestModel> getRandomLabelsData(String filePath, int minLabelsCount, int maxLabelsCount) {

    List<LabelRequestModel> requests = new ArrayList<>();
    
    // TODO : заменить на txt-формат, чтобы data была просто List<String>, а не List<String[]>
    // Прочитать в переменную весь файл. 
    List<String[]> data = openFile(filePath);
    
    // Определить количество проектов
    int labelsCount;
    if (minLabelsCount = null || maxLabelsCount == null) {
        labelsCount = data.size();
    } else {
        Random randomLabelsCount = new Random();
        int labelsCount = randomLabelsCount.nextInt(
                maxLabelsCount - minLabelsCount + 1) + minLabelsCount;
        labelsCount = Math.min(labelsCount, data.size());
    }

    // Собираем запрос в List<LabelRequestModel>
    for (int i = 1; i <= labelsCount; i++) {

      // Имя
      Random randomNamesIndex = new Random();
      int nameIndex = randomNamesIndex.nextInt(data.size());
      String name = data.get(nameIndex)[0];
      data.remove(nameIndex);

      // Цвет
      Color[] colors = Color.values();
      Random randomColors = new Random();
      Color color = colors[randomColors.nextInt(colors.length)];

      // Любимое
      bool isFavorite = true;

      // Создаем одну модель.
      LabelRequestModel request = LabelRequestModel.builder()
        .name(name)
        .color(color)
        .isFavorite(isFavorite)
        .build();

      // Добавляем модель в список моделей
      requests.add(request);
    }

    return requests;
  }
  
  public List<LabelRequestModel> getRandomLabelsData(String filePath, int labelsCount) {
        return getLabelsData(filePath, labelsCount, labelsCount);
  }

  public List<LabelRequestModel> getRandomLabelsData(String filePath) {
        return getLabelsData(filePath, null, null);
  }

  // TODO : убрать в АПИ? (делает ровно тоже самое)
  public List<LabelResponseModel> createLabels(List<LabelRequestModel> requests) {

    LabelsApi api = new LabelsApi();
    List<LabelResponseModel> responses = new ArrayList<>();

    for (LabelRequestModel request : requests) {
      responses.add(api.createNewProject(request));
    }
    
    return responses;
  }
    
  // ====================================================

  
  // === РАЗДЕЛЫ =================================================
  
  public List<SectionRequestModel> getRandomSectionsData(List<ProjectResponseModel> projects, String filePath, int minSectionsCount, int maxSectionsCount) {

    List<SectionRequestModel> requests = new ArrayList<>();
    
    // Сколько всего проектов?
    int projectsCount = projects.size();
    
    // TODO : заменить на txt-формат, чтобы data была просто List<String>, а не List<String[]>
    // Прочитать в переменную весь файл. 
    List<String[]> data = openFile(filePath);

    for(List<ProjectResponseModel> project : projects) {

      // Определяем id проекта
      String projectId = project.id;

      List<String[]> dataCopy = new ArrayList<>(data);
      
      // Определить количество секций
      int sectionsCount;
      if (minSectionsCount = null || maxSectionsCount == null) {
          sectionsCount = data.size();
      } else {
          Random randomSectionsCount = new Random();
          int sectionsCount = randomSectionsCount.nextInt(
                  maxSectionsCount - minSectionsCount + 1) + minSectionsCount;
          sectionsCount = Math.min(sectionsCount, dataCopy.size());
      }

      // Собираем запрос в List<SectionRequestModel>
      for (int i = 1; i <= sectionsCount; i++) {

        // Имя
        Random randomNamesIndex = new Random();
        int nameIndex = randomNamesIndex.nextInt(dataCopy.size());
        String name = dataCopy.get(nameIndex)[0];
        dataCopy.remove(nameIndex);

        // Создаем одну модель.
        SectionRequestModel request = SectionRequestModel.builder()
          .name(name)
          .projectId(projectId)
          .build();

        // Добавляем запрос в список запросов
        requests.add(request);
      }
    }

    return requests;
  }
  
  public List<SectionRequestModel> getRandomSectionsData(List<ProjectResponseModel> projects, String filePath, int sectionsCount) {
        return getRandomSectionsData(filePath, sectionsCount, sectionsCount);
  }

  public List<SectionRequestModel> getRandomSectionsData(List<ProjectResponseModel> projects, String filePath) {
        return getRandomSectionsData(filePath, null, null);
  }

  // TODO : убрать в АПИ? (делает ровно тоже самое)
  public List<SectionResponseModel> createSections(List<SectionRequestModel> requests) {

    SectionsApi api = new SectionsApi();
    List<SectionResponseModel> responses = new ArrayList<>();

    for (SectionRequestModel request : requests) {
      responses.add(api.createNewSection(request));
    }
    
    return responses;
  }

  // ====================================================
  
  
  // === ПРОЕКТЫ =================================================
  
  public List<ProjectRequestModel> getRandomProjectsData(String filePath, int minProjectsCount, int maxProjectsCount) {

    List<ProjectRequestModel> requests = new ArrayList<>();
    
    // TODO : заменить на txt-формат, чтобы data была просто List<String>, а не List<String[]>
    // Прочитать в переменную весь файл. 
    List<String[]> data = openFile(filePath);
    
    // Определить количество проектов
    int projectsCount;
    if (minProjectsCount = null || maxProjectsCount == null) {
        projectsCount = data.size();
    } else {
        Random randomProjectsCount = new Random();
        int projectsCount = randomProjectsCount.nextInt(
                maxProjectsCount - minProjectsCount + 1) + minProjectsCount;
        projectsCount = Math.min(projectsCount, data.size());
    }

    // Собираем запрос в List<ProjectRequestModel>
    for (int i = 1; i <= projectsCount; i++) {

      // Имя
      Random randomNamesIndex = new Random();
      int nameIndex = randomNamesIndex.nextInt(data.size());
      String name = data.get(nameIndex)[0];
      data.remove(nameIndex);

      // Цвет
      Color[] colors = Color.values();
      Random randomColors = new Random();
      Color color = colors[randomColors.nextInt(colors.length)];

      // Любимое
      bool isFavorite = false;

      // Стиль
      ViewStyle viewStyle = BOARD;

      // Создаем одну модель.
      ProjectRequestModel request = ProjectRequestModel.builder()
        .name(name)
        .color(color)
        .isFavorite(isFavorite)
        .viewStyle(viewStyle)
        .build();

      // Добавляем модель в список моделей
      requests.add(request);
    }

    return requests;
  }
  
  public List<ProjectRequestModel> getRandomProjectsData(String filePath, int projectsCount) {
        return getProjectsData(filePath, projectsCount, projectsCount);
  }

  public List<ProjectRequestModel> getRandomProjectsData(String filePath) {
        return getProjectsData(filePath, null, null);
  }

  // TODO : убрать в АПИ? (делает ровно тоже самое)
  public List<ProjectResponseModel> createProjects(List<ProjectRequestModel> requests) {

    ProjectsApi api = new ProjectsApi();
    List<ProjectResponseModel> responses = new ArrayList<>();

    for (ProjectRequestModel request : requests) {
      responses.add(api.createNewProject(request));
    }
    
    return responses;
  }
    
  // ====================================================
    
    // TODO : заменить на txt-формат, чтобы data была просто List<String>, а не List<String[]>
    // Прочитать в переменную весь файл. 
    List<String[]> data = openFile("data/labelsList.csv");
    
    // Определить количество проектов
    int projectCount;
    if (minProjectsCount = null || maxProjectsCount == null) {
        projectCount = data.size();
    } else {
        Random randomProjectsCount = new Random();
        int projectsCount = randomProjectsCount.nextInt(
                maxProjectsCount - minProjectsCount + 1) + minProjectsCount;
        projectsCount = Math.min(projectsCount, data.size());
    }

    // Собираем запрос в List<ProjectRequestModel>
    List<ProjectRequestModel> requests = new ArrayList<>();
    for (int i = 1; i <= projectsCount; i++) {

      // Имя
      Random randomNamesIndex = new Random();
      int nameIndex = randomNamesIndex.nextInt(data.size());
      String name = data.get(nameIndex)[0];
      data.remove(nameIndex);

      // Цвет
      Color[] colors = Color.values();
      Random randomColors = new Random();
      Color color = colors[randomColors.nextInt(colors.length)];

      // Любимое
      bool isFavorite = false;

      // Стиль
      ViewStyle viewStyle = BOARD;

      // Создаем одну модель.
      ProjectRequestModel request = ProjectRequestModel.builder()
        .name(name)
        .color(color)
        .isFavorite(isFavorite)
        .viewStyle(viewStyle)
        .build();

      // Добавляем модель в список моделей
      requests.add(request);
    }

    return requestModels;
  }
  
  public List<ProjectRequestModel> getRandomProjectsData(String filePath, int projectsCount) {
        return getProjectsData(filePath, projectsCount, projectsCount);
  }

  public List<ProjectRequestModel> getRandomProjectsData(String filePath) {
        return getProjectsData(filePath, null, null);
  }

  // TODO : убрать в АПИ? (делает ровно тоже самое)
  public List<ProjectResponseModel> createProjects(List<ProjectRequestModel> requests) {

    ProjectsApi api = new ProjectsApi();
    List<ProjectResponseModel> responses = new ArrayList<>();

    for (ProjectRequestModel request : requests) {
      responses.add(api.createNewProject(request));
    }
    
    return responses;
  }

  // ====================================================


  

  /*
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

  */
}
