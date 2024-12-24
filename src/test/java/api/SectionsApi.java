package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class SectionsApi extends BaseApi {

    @Step("[API] Создать новый раздел.")
    public SectionResponseModel createNewSection(SectionRequestModel sectionData) {

        return given()
                .spec(requestPostSpec)
                .body(sectionData)
                .when()
                .post(SECTIONS_ENDPOINT)
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

    @Step("[API] Обновить раздел.")
    public SectionResponseModel updateSection(String sectionId, SectionRequestModel sectionData) {

        return given()
                .spec(requestPostSpec)
                .body(sectionData)
                .when()
                .post(SECTIONS_ENDPOINT + sectionId)
                .then()
                .spec(responseSpec200)
                .extract().as(SectionResponseModel.class);
    }

    public SectionResponseModel updateSection(String sectionId, String newName) {

        SectionRequestModel sectionData = SectionRequestModel.builder()
                .name(newName)
                .build();

        return updateSection(sectionId, sectionData);
    }

    @Step("[API] Получить раздел.")
    public SectionResponseModel getSection(String sectionId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(SECTIONS_ENDPOINT + sectionId)
                .then()
                .spec(responseSpec200)
                .extract().as(SectionResponseModel.class);
    }

    @Step("[API] Получить все разделы в проекте.")
    public List<SectionResponseModel> getAllSections(String projectId) {

        RequestSpecification request = given()
                .queryParam("project_id", projectId);

        return getAllSections(request);
    }

    @Step("[API] Получить все разделы во всех проектах.")
    public List<SectionResponseModel> getAllSections() {

        RequestSpecification request = given();

        return getAllSections(request);
    }

    private List<SectionResponseModel> getAllSections(RequestSpecification request) {

        return request
                .spec(requestGetSpec)
                .when()
                .get(SECTIONS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", SectionResponseModel.class);
    }

    @Step("[API] Удалить раздел.")
    public void deleteSection(String sectionId) {

        given()
                .spec(requestGetSpec)
                .when()
                .delete(SECTIONS_ENDPOINT + sectionId)
                .then()
                .spec(responseSpec204);
    }

    @Step("[API] Удалить все разделы в проекте.")
    public void deleteAllSectionInProject(String projectId) {

        List<SectionResponseModel> sections = getAllSections(projectId);

        for (SectionResponseModel section : sections) {
            deleteSection(section.getId());
        }
    }

}