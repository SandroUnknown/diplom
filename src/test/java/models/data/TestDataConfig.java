package models.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestDataConfig {

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

    public static class TestDataConfigBuilder {
        public TestDataConfigBuilder() {
            this.createLabels = false;
            this.createProjects = false;
            this.createCommentsInProjects = false;
            this.createTasksInProjects = false;
            this.addLabelsForTasksInProjects = false;
            this.createCommentsInTasksInProjects = false;
            this.createSections = false;
            this.createTasksInSections = false;
            this.addLabelsForTasksInSections = false;
            this.createCommentsInTasksInSections = false;
        }
    }
}