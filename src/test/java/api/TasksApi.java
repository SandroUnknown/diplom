package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.tasks.TaskRequestModel;
import models.tasks.TaskResponseModel;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class TasksApi extends BaseApi {

    @Step("[API] Создать новую задачу.")
    public TaskResponseModel createNewTask(TaskRequestModel taskData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(taskData)
                .when()
                .post(TASKS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract().as(TaskResponseModel.class);
    }

    public TaskResponseModel createNewTask(String sectionId, String taskContent) {

        TaskRequestModel taskData = TaskRequestModel.builder()
                .sectionId(sectionId)
                .content(taskContent)
                .build();

        return createNewTask(taskData);
    }

    @Step("[API] Обновить задачу.")
    public TaskResponseModel updateTask(String taskId, TaskRequestModel taskData) {

        return given()
                .spec(requestPostWithIdSpec)
                .body(taskData)
                .when()
                .post(TASKS_ENDPOINT + taskId)
                .then()
                .spec(responseSpec200)
                .extract().as(TaskResponseModel.class);
    }

    public TaskResponseModel updateTask(String taskId, String taskContent) {

        TaskRequestModel taskData = TaskRequestModel.builder()
                .content(taskContent)
                .build();

        return updateTask(taskId, taskData);
    }

    @Step("[API] Получить задачу.")
    public TaskResponseModel getTask(String taskId) {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(TASKS_ENDPOINT + taskId)
                .then()
                .spec(responseSpec200)
                .extract().as(TaskResponseModel.class);
    }

    @Step("[API] Получить задачи (с фильтром).")
    public List<TaskResponseModel> getTasksWithFilter(HashMap<String, String> params) {

        // TODO : заменить на enum?
        List<String> filters = List.of(
                "filter",
                "ids",
                "project_id",
                "section_id",
                "label"
        );

        RequestSpecification request = given();

        for (String filter : filters) {
            String value = params.get(filter);
            if (value != null) {
                request.queryParam(filter, value);
            }
        }

        return request
                .spec(requestGetSpec)
                .when()
                .get(TASKS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", TaskResponseModel.class);
    }

    @Step("[API] Получить все задачи пользователя.")
    public List<TaskResponseModel> getAllTasks() {

        return given()
                .spec(requestGetSpec)
                .when()
                .get(TASKS_ENDPOINT)
                .then()
                .spec(responseSpec200)
                .extract()
                .jsonPath()
                .getList(".", TaskResponseModel.class);
    }

    @Step("[API] Закрыть задачу.")
    public void closeTask(String taskId) {

        given()
                .spec(requestPostSpec)
                .when()
                .post(String.format("%s%s/close", TASKS_ENDPOINT, taskId))
                .then()
                .spec(responseSpec204);
    }

    @Step("[API] Открыть ранее закрытую задачу.")
    public void reopenTask(String taskId) {

        given()
                .spec(requestPostSpec)
                .when()
                .post(String.format("%s%s/reopen", TASKS_ENDPOINT, taskId))
                .then()
                .spec(responseSpec204);
    }

    @Step("[API] Удалить задачу.")
    public void deleteTask(String taskId) {

        given()
                .spec(requestDeleteSpec)
                .when()
                .delete(TASKS_ENDPOINT + taskId)
                .then()
                .spec(responseSpec204);
    }
}