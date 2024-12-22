package models.labels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelResponseModel {

    private String id;
    private String name;
    //@JsonProperty("new_name") private String newName;
    private Color color;
    private int order;
    @JsonProperty("is_favorite") private boolean isFavorite;
}
