package models.apps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadedAppsListResponseModel {
    @JsonProperty("app_name") private String appName;
    @JsonProperty("app_url") private String appUrl;
}