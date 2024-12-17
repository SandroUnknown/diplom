package models.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectRequestModel {

    private String name;
    @JsonProperty("parent_id") private String parentId;
    private String color;
    @JsonProperty("is_favorite") private boolean isFavorite;
    @JsonProperty("view_style") private String viewStyle;
}