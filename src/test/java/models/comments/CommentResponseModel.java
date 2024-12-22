package models.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentResponseModel {

    @JsonProperty("task_id") private String taskId;
    @JsonProperty("project_id") private String projectId;
    private String id;
    private String content;
    @JsonProperty("posted_at") private String postedAt;
    private Attachment attachment;

    private static class Attachment {
        @JsonProperty("resource_type") private String resourceType;
        @JsonProperty("file_url") private String fileUrl;
        @JsonProperty("file_type") private String fileType;
        @JsonProperty("file_name") private String fileName;
    }
}
