package specs;

import drivers.CredentialsConfigDriver;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.listener.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class Specification {

    static String token = new CredentialsConfigDriver().getTodoistToken();

    public static final RequestSpecification requestGetSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header("Authorization", "Bearer " + token);

    public static final RequestSpecification requestDeleteSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header("Authorization", "Bearer " + token);

    public static final RequestSpecification requestPostSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(JSON);

    public static final RequestSpecification requestPostWithIdSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header("Authorization", "Bearer " + token)
            .header("X-Request-Id", "$(uuidgen)")
            .contentType(JSON);

    public static ResponseSpecification responseSpec(int statusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(ALL)
                .build();
    }

    public static final ResponseSpecification responseSpec200 = responseSpec(200);
    public static final ResponseSpecification responseSpec204 = responseSpec(204);
}