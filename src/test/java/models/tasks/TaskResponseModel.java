package models.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskResponseModel {

    private String content;
    private String description;
    private int order;
    private int priority;
    private List<String> labels;
    private String id;
    @JsonProperty("project_id") private String projectId;
    @JsonProperty("section_id") private String sectionId;
    @JsonProperty("parent_id") private String parentId;
    @JsonProperty("assignee_id") private String assigneeId;
    @JsonProperty("assigner_id") private String assignerId;
    @JsonProperty("created_at") private String createdAt;
    @JsonProperty("creator_id") private String creatorId;
    @JsonProperty("comment_count") private int commentCount;
    @JsonProperty("is_completed") private boolean isCompleted;
    private String url;

    private Duration duration;
    private Due due;

    // TODO : Public???
    private static class Duration {
        private int amount;
        private String unit;
    }

    private static class Due {
        @JsonProperty("string") private String str;
        private String date;
        @JsonProperty("is_recurring") private boolean isRecurring;
        private String datetime;
        private String timezone;
    }
}