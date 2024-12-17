package models.sections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionResponseModel {

    private String id;
    @JsonProperty("project_id") private String projectId;
    private int order;
    private String name;
}