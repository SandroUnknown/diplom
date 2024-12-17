package models.projects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectResponseModel {

    private String id;
    private String name;
    @JsonProperty("comment_count") private int commentCount;
    private String color;
    @JsonProperty("is_shared") private boolean isShared;
    private String order;
    @JsonProperty("is_favorite") private boolean isFavorite;
    @JsonProperty("is_inbox_project") private boolean isInboxProject;
    @JsonProperty("is_team_inbox") private boolean isTeamInbox;
    @JsonProperty("view_style") private String viewStyle;
    private String url;
    @JsonProperty("parent_id") private String parentId;
}