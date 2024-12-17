package models.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Color;
import enums.ViewStyle;
import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectRequestModel {

    private String name;
    @JsonProperty("parent_id") private String parentId;
    private Color color;
    //private String color;
    @JsonProperty("is_favorite") private boolean isFavorite;
    @JsonProperty("view_style") private ViewStyle viewStyle;


}