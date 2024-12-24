package models.labels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Color;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabelRequestModel {

    private String name;
    private Color color;
    private int order;
    @JsonProperty("is_favorite") private boolean isFavorite;
}
