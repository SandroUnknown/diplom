package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class PostTests extends TestBase {

    @Test
    void createUserTest() {

        CreateUserRequestModel request = new CreateUserRequestModel();
        request.setName("alex");
        request.setJob("qa engineer");

        CreateUserSuccessfulResponseModel response = given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(request)
                .contentType(JSON)

        .when()
                .post("/users")

        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateUserSuccessfulResponseModel.class);

        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getJob()).isEqualTo(request.getJob());
        assertThat(response.getId()).isNotEmpty();
        assertThat(response.getCreatedAt()).isNotEmpty();
    }

    @Test
    void successfulRegistrationTest() {

        RegistrationRequestModel request = new RegistrationRequestModel();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        RegistrationSuccessfulResponseModel response = given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(request)
                .contentType(JSON)

        .when()
                .post("/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegistrationSuccessfulResponseModel.class);

        assertThat(response.getId()).isNotEqualTo(0);
        assertThat(response.getToken()).isNotEmpty();
    }

    @Test
    void unsuccessfulRegistrationMissingPasswordTest() {

        RegistrationRequestModel request = new RegistrationRequestModel();
        request.setEmail("eve.holt@reqres.in");

        RegistrationMissingEmailOrPasswordResponseModel response = given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(request)
                .contentType(JSON)

        .when()
                .post("/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(RegistrationMissingEmailOrPasswordResponseModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    void unsuccessfulRegistrationMissingEmailTest() {

        RegistrationRequestModel request = new RegistrationRequestModel();
        request.setPassword("pistol");

        RegistrationMissingEmailOrPasswordResponseModel response = given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(request)
                .contentType(JSON)

        .when()
                .post("/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(RegistrationMissingEmailOrPasswordResponseModel.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }
}