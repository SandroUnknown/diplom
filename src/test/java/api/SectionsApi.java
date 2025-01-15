package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.sections.SectionRequestModel;
import models.sections.SectionResponseModel;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specification.*;

public class SectionsApi extends BaseApi {

    @Step("Создать новый раздел")
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

    @Step("Обновить раздел")
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

    @Step("Получить все разделы в проекте")
    public List<SectionResponseModel> getAllSections(String projectId) {

        RequestSpecification request = given()
                .queryParam("project_id", projectId);

        return getAllSections(request);
    }

    @Step("Получить все разделы во всех проектах")
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

    @Step("Удалить раздел")
    public void deleteSection(String sectionId) {

        given()
                .spec(requestGetSpec)
                .when()
                .delete(SECTIONS_ENDPOINT + sectionId)
                .then()
                .spec(responseSpec204);
    }

    @Step("Удалить все разделы в проекте")
    public void deleteAllSectionInProject(String projectId) {

        List<SectionResponseModel> sections = getAllSections(projectId);

        for (SectionResponseModel section : sections) {
            deleteSection(section.getId());
        }
    }

    @Step("Проверить, что раздел был успешно создан в указанном месте [API]")
    public void checkSection(int sectionIndex, String expectedSectionName) {

        String actualSectionName = getAllSections().get(sectionIndex).getName();

        assertThat(actualSectionName).isEqualTo(expectedSectionName);
    }

    @Step("Проверить, что раздел был успешно удалён [API]")
    public void checkDeleteSection(String expectedSectionName) {

        List<SectionResponseModel> actualSections = getAllSections();

        for (SectionResponseModel actualSection : actualSections) {
            String actualSectionName = actualSection.getName();
            assertThat(actualSectionName).isNotEqualTo(expectedSectionName);
        }
    }
}