package models.labels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabelRequestModel {

    private String name;
    private Color color;
    private int order;
    @JsonProperty("is_favorite") private boolean isFavorite;
}
