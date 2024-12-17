package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class SectionsApi {

    @Step("Создать новый раздел (API)")
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

    @Step("Обновить раздел по ID (API)")
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

    @Step("Получить один раздел по ID (API)")
    public SectionResponseModel getSection(String sectionId) {

        return
                given()
                        .spec(requestGetSpec)
                        .when()
                        .get("/sections/" + sectionId)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(SectionResponseModel.class);
    }

    @Step("Получить все разделы (в рамках одного проекта) (API)")
    public List<SectionResponseModel> getAllSectionsInProject(String projectId) {
        RequestSpecification request = given()
                .queryParam("project_id", projectId);
        return getAllSections(request);
    }

    @Step("Получить все разделы (во всех проектах) (API)")
    public List<SectionResponseModel> getAllSections() {
        RequestSpecification request = given();
        return getAllSections(request);
    }

    private List<SectionResponseModel> getAllSections(RequestSpecification request) {

        return request
                .spec(requestGetSpec)
                .when()
                .get("/sections")
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", SectionResponseModel.class);
    }

    @Step("Удалить один раздел по ID (API)")
    public void deleteSection(String sectionId) {

        given()
                .spec(requestGetSpec)
                .when()
                .delete("/sections/" + sectionId)
                .then()
                .spec(responseSpec204);
    }

    @Step("Удалить все разделы в проекте (API)")
    public void deleteAllSectionInProject(String projectId) {

        List<SectionResponseModel> sections = getAllSectionsInProject(projectId);

        for (SectionResponseModel section : sections) {
            deleteSection(section.getId());
        }
    }

}
