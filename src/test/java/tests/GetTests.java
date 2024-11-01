package tests;

import models.SingleUserSuccessfulResponseModel;
import models.UserListSuccessfulResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class GetTests extends TestBase {

    @Test
    void successfulGetSingleUserTest() {

        SingleUserSuccessfulResponseModel response = given()
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .get("/users/2")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(SingleUserSuccessfulResponseModel.class);

        assertThat(response.getData().getId()).isEqualTo(2);
        assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(response.getData().getFirst_name()).isEqualTo("Janet");
        assertThat(response.getData().getLast_name()).isEqualTo("Weaver");
        assertThat(response.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");

        assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        assertThat(response.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    void singleUserNotFoundTest() {

        given()
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .get("/users/23")

        .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void getUsersListTest() {

        UserListSuccessfulResponseModel response = given()
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .get("/users?page=2")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UserListSuccessfulResponseModel.class);

        assertThat(response.getPage()).isEqualTo(2);
        assertThat(response.getPer_page()).isEqualTo(6);
        assertThat(response.getTotal()).isEqualTo(12);
        assertThat(response.getTotal_pages()).isEqualTo(2);

        assertThat(response.getData().get(3).getId()).isEqualTo(10);
        assertThat(response.getData().get(3).getEmail()).isEqualTo("byron.fields@reqres.in");
        assertThat(response.getData().get(3).getFirst_name()).isEqualTo("Byron");
        assertThat(response.getData().get(3).getLast_name()).isEqualTo("Fields");
        assertThat(response.getData().get(3).getAvatar()).isEqualTo("https://reqres.in/img/faces/10-image.jpg");

        assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        assertThat(response.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }
}