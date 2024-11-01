package tests;

import models.SingleUserNotFoundResponseModel;
import models.SingleUserSuccessfulResponseModel;
import models.UserListSuccessfulResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GET-запросы")
public class GetTests extends TestBase {

    @DisplayName("Проверка данных одиночного пользователя")
    @Test
    void successfulGetSingleUserTest() {

        SingleUserSuccessfulResponseModel response = step("Сделать запрос", ()->
            given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().body()
                    .log().headers()
            .when()
                    .get("/users/2")

            .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(SingleUserSuccessfulResponseModel.class)
        );

        step("Проверить ответ", ()-> {
            assertThat(response.getData().getId()).isEqualTo(2);
            assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(response.getData().getFirst_name()).isEqualTo("Janet");
            assertThat(response.getData().getLast_name()).isEqualTo("Weaver");
            assertThat(response.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");

            assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
            assertThat(response.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }

    @DisplayName("Проверка данных, когда пользователь не найден")
    @Test
    void singleUserNotFoundTest() {

        SingleUserNotFoundResponseModel response = step("Сделать запрос", ()->
            given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().body()
                    .log().headers()
            .when()
                    .get("/users/23")

            .then()
                    .log().status()
                    .log().body()
                    .statusCode(404)
                    .extract().as(SingleUserNotFoundResponseModel.class)
        );

        step("Проверить ответ", ()->
            assertThat(response).isNotNull()
        );
    }

    @DisplayName("Проверка данных списка всех пользователей")
    @Test
    void getUsersListTest() {

        UserListSuccessfulResponseModel response = step("Сделать запрос", ()->
            given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().body()
                    .log().headers()
            .when()
                    .get("/users?page=2")

            .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(UserListSuccessfulResponseModel.class)
        );

        step("Проверить ответ", ()-> {
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
        });
    }
}