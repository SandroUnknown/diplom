package models.data;

import lombok.Data;
import models.comments.CommentResponseModel;
import models.labels.LabelResponseModel;
import models.projects.ProjectResponseModel;
import models.sections.SectionResponseModel;
import models.tasks.TaskResponseModel;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestDataModel {

    public TestDataModel() {
        this.labels = new ArrayList<>();                        // +
        this.projects = new ArrayList<>();                      // +
        this.commentsInProjects = new ArrayList<>();            // +
        this.tasksInProjects = new ArrayList<>();               // + +
        this.commentsInTasksInProjects = new ArrayList<>();     // +
        this.sections = new ArrayList<>();                      // +
        this.tasksInSections = new ArrayList<>();               // + +
        this.commentsInTasksInSections = new ArrayList<>();     // +
    }


    private List<LabelResponseModel> labels;
    private List<ProjectResponseModel> projects;

        private List<CommentResponseModel> commentsInProjects;
        private List<TaskResponseModel> tasksInProjects;
            private List<CommentResponseModel> commentsInTasksInProjects;
        private List<SectionResponseModel> sections;
            private List<TaskResponseModel> tasksInSections;
                private List<CommentResponseModel> commentsInTasksInSections;
}
