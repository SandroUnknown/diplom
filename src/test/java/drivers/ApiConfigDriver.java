package drivers;

import config.ApiConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;

public class ApiConfigDriver {

    private final ApiConfig config;

    public ApiConfigDriver() {
        this.config = ConfigFactory.create(ApiConfig.class, System.getProperties());
        createDriver();
    }

    private void createDriver() {
        RestAssured.baseURI = config.getBaseUri();
        System.out.println();
    }
}