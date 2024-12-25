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
      responses.add(api.createNewLabel(request));
    }
    
    return responses;
  }
    
// = конец МЕТКИ ===================================================


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
    
  // === конец ПРОЕКТЫ =================================================


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
          sectionsCount = dataCopy.size();
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
        return getRandomSectionsData(projects, filePath, sectionsCount, sectionsCount);
  }

  public List<SectionRequestModel> getRandomSectionsData(List<ProjectResponseModel> projects, String filePath) {
        return getRandomSectionsData(projects, filePath, null, null);
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

  // === конец РАЗДЕЛЫ =================================================


  // === ЗАДАЧИ =================================================

  // min + max, с метками
  public List<TaskRequestModel> getRandomTasksData(List<SectionResponseModel> sections, String filePath, int minTasksCount, int maxTasksCount, List<LabelResponseModel> labels, int minLabelsCount, int maxLabelsCount) {

    // TODO : вероятно надо вынести data.size() в переменную, т.к. используется минимум трижды
    
    List<TaskRequestModel> requests = new ArrayList<>();
    
    // Сколько всего разделов?
    int sectionsCount = sections.size();
    
    // TODO : заменить на txt-формат, чтобы data была просто List<String>, а не List<String[]>
    // Прочитать в переменную весь файл. 
    List<String[]> data = openFile(filePath);

    // Делаем из объектов-меток только список имен меток (остальные данные нам не нужны)
    List<String> labelsName = new ArrayList<>();
    for(List<LabelResponseModel> label : labels) {
      labelsName.add(label.name);
    }

    for(List<SectionResponseModel> section : sections) {

      // Определяем id раздела
      String sectionId = section.id;

      List<String[]> dataCopy = new ArrayList<>(data);
      
      // Определить количество разделов
      int tasksCount;
      if (minTasksCount = null || maxTasksCount == null) {
          tasksCount = dataCopy.size();
      } else {
          Random randomTasksCount = new Random();
          int tasksCount = randomTasksCount.nextInt(
                  maxTasksCount - minTasksCount + 1) + minTasksCount;
          tasksCount = Math.min(tasksCount, dataCopy.size());
      }

      // Собираем запрос в List<TaskRequestModel>
      for (int i = 1; i <= tasksCount; i++) {

        // Контент
        Random randomContentsIndex = new Random();
        int contentIndex = randomContentsIndex.nextInt(dataCopy.size());
        String content = dataCopy.get(contentIndex)[0];
        dataCopy.remove(contentIndex);

        // Приорити
        Random randomPriority = new Random();
        int priority = randomPriority.nextInt(4) + 1;

        // Метки ---------------------------------------------------------------------------
        /*  // Генерируем количество меток в задаче
        Random randomLabelsCount = new Random();
        //int labelsCount = randomLabelsCount.nextInt(3);
        int labelsCount = randomTasksCount.nextInt(
                  maxLabelsCount - minLabelsCount + 1) + minLabelsCount;
        labelsCount = Math.min(labelsCount, labelsName.size());*/

        // Генерируем количество меток В ЗАДАЧЕ
        int labelsCount;
        if (minLabelsCount = null || maxLabelsCount == null) {
          labelsCount = labelsName.size();
        } else {
          Random randomLabelsCount = new Random();
          labelsCount = randomTasksCount.nextInt(
                  maxLabelsCount - minLabelsCount + 1) + minLabelsCount;
          labelsCount = Math.min(labelsCount, labelsName.size());
        }

        // Создаем список из меток, чтобы потом добавить В ЗАДАЧУ
        List<String> labelsNameCopy = new ArrayList<>(labelsName);
        List<String> labelsList = new ArrayList<>();
        for (int k = 1; k <= labelsCount; k++) {
          Random randomLabelsIndex = new Random();
          int labelIndex = randomLabelsIndex.nextInt(labelsNameCopy.size()); // TODO : проверить, вероятно будет генерировать даже при 0 размере!!!! 
          labelsList.add(labelsNameCopy.get(labelIndex));
          labelsNameCopy.remove(labelsIndex);
        }
        // ----------------------------------------------------------------------------------

        // Создаем одну модель.
        TaskRequestModel request = TaskRequestModel.builder()
          .content(content)
          .sectionId(sectionId)
          .priority(priority)
          .labels(labelsList)
          .build();

        // Добавляем запрос в список запросов
        requests.add(request);
      }
    }

    return requests;
  }

  // min + max, но без меток
  public List<TaskRequestModel> getRandomTasksData(List<SectionResponseModel> sections, String filePath, int minTasksCount, int maxTasksCount) {
        // TODO : подать в метках пустой список вместо NULL
        return getRandomTasksData(sections, filePath, minTasksCount, maxTasksCount, null, 0, 0);
  }

  // конкретное число, c метками
  public List<TaskRequestModel> getRandomTasksData(List<SectionResponseModel> sections, String filePath, int tasksCount, List<LabelResponseModel> labels, int labelsCount) {
        return getRandomTasksData(sections, filePath, tasksCount, tasksCount, labels, labelsCount, labelsCount);
  }
  
  // конкретное число, но без меток
  public List<TaskRequestModel> getRandomTasksData(List<SectionResponseModel> sections, String filePath, int tasksCount) {
        return getRandomTasksData(sections, filePath, tasksCount, tasksCount, null, 0, 0);
  }

  // все, со всеми метками
  public List<TaskRequestModel> getRandomTasksData(List<SectionResponseModel> sections, String filePath, List<LabelResponseModel> labels) {
        // TODO : подать в метках пустой список вместо NULL
        return getRandomTasksData(sections, filePath, null, null, labels, null, null);
  }
  
  // все, но без меток
  public List<TaskRequestModel> getRandomTasksData(List<SectionResponseModel> sections, String filePath) {
        // TODO : подать в метках пустой список вместо NULL
        return getRandomTasksData(sections, filePath, null, null, null, null, null);
  }

  // TODO : убрать в АПИ? (делает ровно тоже самое)
  // TODO : ещё и метод этот одинаковый, может сделать дженериком?
  public List<TaskResponseModel> createTasks(List<TaskRequestModel> requests) {

    TasksApi api = new TasksApi();
    List<TaskResponseModel> responses = new ArrayList<>();

    for (TaskRequestModel request : requests) {
      responses.add(api.createNewTask(request));
    }
    
    return responses;
  }

  // === конец ЗАДАЧИ =================================================


  // === КОММЕНТЫ =================================================
  
  public List<CommentRequestModel> getRandomCommentsData(List<TaskResponseModel> tasks, String filePath, int minCommentsCount, int maxCommentsCount) {

    List<CommentRequestModel> requests = new ArrayList<>();
    
    // Сколько всего проектов?
    int tasksCount = tasks.size();
    
    // TODO : заменить на txt-формат, чтобы data была просто List<String>, а не List<String[]>
    // Прочитать в переменную весь файл. 
    List<String[]> data = openFile(filePath);

    for(List<TaskResponseModel> task : tasks) {

      // Определяем id проекта
      String taskId = task.id;

      List<String[]> dataCopy = new ArrayList<>(data);
      
      // Определить количество секций
      int commentsCount;
      if (minCommentsCount = null || maxCommentsCount == null) {
          commentsCount = dataCopy.size();
      } else {
          Random randomCommentsCount = new Random();
          int commentsCount = randomCommentsCount.nextInt(
                  maxCommentsCount - minCommentsCount + 1) + minCommentsCount;
          commentsCount = Math.min(commentsCount, dataCopy.size());
      }

      // Собираем запрос в List<CommentRequestModel>
      for (int i = 1; i <= commentsCount; i++) {

        // Контент
        Random randomContentsIndex = new Random();
        int contentIndex = randomContentsIndex.nextInt(dataCopy.size());
        String content = dataCopy.get(contentIndex)[0];
        dataCopy.remove(contentIndex);

        // Создаем одну модель.
        CommentRequestModel request = CommentRequestModel.builder()
          .content(content)
          .taskId(taskId)
          .build();

        // Добавляем запрос в список запросов
        requests.add(request);
      }
    }

    return requests;
  }
  
  public List<CommentRequestModel> getRandomCommentsData(List<TaskResponseModel> tasks, String filePath, int commentsCount) {
        return getRandomCommentsData(tasks, filePath, commentsCount, commentsCount);
  }

  public List<CommentRequestModel> getRandomCommentsData(List<TaskResponseModel> tasks, String filePath) {
        return getRandomCommentsData(tasks, filePath, null, null);
  }

  // TODO : убрать в АПИ? (делает ровно тоже самое)
  public List<CommentResponseModel> createComments(List<CommentRequestModel> requests) {

    CommentsApi api = new CommentsApi();
    List<CommentResponseModel> responses = new ArrayList<>();

    for (CommentRequestModel request : requests) {
      responses.add(api.createNewComment(request));
    }
    
    return responses;
  }

  // === конец КОММЕНТЫ =================================================

  


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
