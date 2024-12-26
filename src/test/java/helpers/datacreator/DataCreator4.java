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

public class DataCreator4 {


private void createLabels(List<AccountData> accountData, List<AccountData> data, bool excludeLabels) {

    if (!excludeLabels) {
        LabelsApi api = new LabelsApi();
        for (int i = 0; i < data.labels.size(); i++) {
            LabelResponseModel label = data.labels.get(i);
            LabelRequestModel request = LabelRequestModel.builder()
                .name(label.name)
                .color(label.color)
                .isFavorite(label.isFavorite)
                .build();   

            label = api.createLabel(request);
            accountData.labels.add(label);
        }
    }
}

private HashMap<String, String> createProjects(List<AccountData> accountData, List<AccountData> data, ENUM setDeepLevel) {

    HashMap<String, String> newId = new HashMap();
    
    // Сравнивать уровень этого метода с уровнем, который я задал?
    thisDeepLevel = ENUM.PROJECT;

    if (setDeepLevel >= thisDeepLevel) {    // взять их id?
    
        ProjectsApi api = new ProjectsApi();     
        for (int i = 0; i < data.projects.size(); i++) {
            ProjectResponseModel project = data.projects.get(i);
            ProjectRequestModel request = ProjectRequestModel.builder()
                .name(project.name)
                .color(project.color)
                .viewStyle(project.viewStyle)
                .build();  

            project = api.createProject(request);
            accountData.projects.add(project);
            newId.add(i, project.id);
        }
    }
    return newId;
}


private void createCommentsInProjects(List<AccountData> accountData, List<AccountData> data, ENUM setDeepLevel, HashMap<String, String> newProjectId) {
    
    // Сравнивать уровень этого метода с уровнем, который я задал?
    thisDeepLevel = ENUM.COMMENTS_IN_PROJECT;

    if (setDeepLevel >= thisDeepLevel) {    // взять их id?
    
        CommentsApi api = new CommentsApi();     
        for (int i = 0; i < data.commentsInProjects.size(); i++) {
            CommentResponseModel comment = data.commentsInProjects.get(i);
            CommentRequestModel request = CommentRequestModel.builder()
                .projectId(newProjectId.get(i))  // вероятно i к стрингу привести
                .content(comment.content)        // TODO : заменить на поля комментов
                .build();  

            comment = api.createCommentInProject(request);
            accountData.commentsInProjects.add(comment);
        }
    }
}

private HashMap<String, String> createSections(List<AccountData> accountData, List<AccountData> data, ENUM setDeepLevel, HashMap<String, String> newProjectId) {

    HashMap<String, String> newId = new HashMap();
    
    // Сравнивать уровень этого метода с уровнем, который я задал?
    thisDeepLevel = ENUM.SECTION;

    if (setDeepLevel >= thisDeepLevel) {    // взять их id?
    
        SectionsApi api = new SectionsApi();     
        for (int i = 0; i < data.sections.size(); i++) {
            SectionResponseModel section = data.sections.get(i);
            SectionRequestModel request = SectionRequestModel.builder()
                .projectId(newProjectId.get(i))  // вероятно i к стрингу привести
                .name(section.name)
                .build();  

            section = api.createSection(request);
            accountData.sections.add(section);
            newId.add(i, section.id);
        }
    }
    return newId;
}

private HashMap<String, String> createTasks(List<AccountData> accountData, List<AccountData> data, ENUM setDeepLevel, HashMap<String, String> newSectionId) {

    HashMap<String, String> newId = new HashMap();
    
    // Сравнивать уровень этого метода с уровнем, который я задал?
    thisDeepLevel = ENUM.TASK;

    if (setDeepLevel >= thisDeepLevel) {    // взять их id?
    
        TasksApi api = new TasksApi();     
        for (int i = 0; i < data.tasks.size(); i++) {
            TaskResponseModel task = data.tasks.get(i);
            TaskRequestModel request = TaskRequestModel.builder()
                .projectId(newSectionId.get(i))  // вероятно i к стрингу привести
                .name(task.name)
                .build();  

            task = api.createTask(request);
            accountData.tasks.add(task);
            newId.add(i, task.id);
        }
    }
    return newId;
}

private void createCommentsInTasks(List<AccountData> accountData, List<AccountData> data, ENUM setDeepLevel, HashMap<String, String> newTaskId) {
    
    // Сравнивать уровень этого метода с уровнем, который я задал?
    thisDeepLevel = ENUM.COMMENTS_IN_TASK;

    if (setDeepLevel >= thisDeepLevel) {    // взять их id?
    
        CommentsApi api = new CommentsApi();     
        for (int i = 0; i < data.commentsInTasks.size(); i++) {
            CommentResponseModel comment = data.commentsInTasks.get(i);
            CommentRequestModel request = CommentRequestModel.builder()
                .taskId(newTaskId.get(i))  // вероятно i к стрингу привести
                .content(comment.content)        // TODO : заменить на поля комментов
                .build();  

            comment = api.createCommentInTask(request);
            accountData.commentsInTasks.add(comment);
        }
    }
}
    


}
