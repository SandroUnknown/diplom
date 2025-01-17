package models.tasks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskRequestModel {

    private String content;
    private String description;
    private int order;
    private int priority;
    private List<String> labels;
    @JsonProperty("project_id") private String projectId;
    @JsonProperty("section_id") private String sectionId;
    @JsonProperty("parent_id") private String parentId;
    @JsonProperty("assignee_id") private String assigneeId;
}