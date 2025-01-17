package models.comments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRequestModel {

    @JsonProperty("task_id") private String taskId;
    @JsonProperty("project_id") private String projectId;
    private String content;
}