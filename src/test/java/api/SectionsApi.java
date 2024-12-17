package api;

import io.qameta.allure.Step;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class SectionsApi {

    @Step("Создать новый раздел (API) - POST")
    public SectionResponseModel createNewSection(SectionRequestModel sectionData) {

        return
                given()
                        .spec(requestPostSpec)
                        .body(sectionData)
                        .when()
                        .post("/sections")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(SectionResponseModel.class);
    }

    public SectionResponseModel createNewSection(String projectId, String sectionName) {

        SectionRequestModel sectionData = SectionRequestModel.builder()
                .projectId(projectId)
                .name(sectionName)
                .build();

        return createNewSection(sectionData);
    }

    @Step("Обновить раздел по ID (API) - POST")
    public SectionResponseModel updateSection(String sectionId, String newName) {

        SectionRequestModel sectionData = SectionRequestModel.builder()
                .name(newName)
                .build();

        return
                given()
                        .spec(requestPostSpec)
                        .body(sectionData)
                        .when()
                        .post("/sections/" + sectionId)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(SectionResponseModel.class);
    }









    //@Step("Получить один раздел по ID (API) - GET")
    //@Step("Получить все разделы (есть фильтр через query по проекту) (API) - GET")
    //@Step("Удалить раздел по ID (API) - DELETE")
    //@Step("Удалить все разделы в проекте (API) - DELETE")

}
