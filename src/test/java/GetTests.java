import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetTests {

    @Test
    void successfulGetSingleUserTest() {
        given()

        .when()
                .log().uri()
                .get("https://reqres.in/api/users/2")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    void singleUserNotFoundTest() {
        given()

        .when()
                .log().uri()
                .get("https://reqres.in/api/users/23")

        .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void getUsersListTest() {
        given()

        .when()
                .log().uri()
                .get("https://reqres.in/api/users?page=2")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data[0].last_name", is("Lawson"))
                .body("data.find { it.id == 9 }.first_name", is("Tobias"));
    }
}