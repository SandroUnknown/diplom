package models.labels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Color;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelResponseModel {

    private String id;
    private String name;
    private Color color;
    private int order;
    @JsonProperty("is_favorite") private boolean isFavorite;
}
