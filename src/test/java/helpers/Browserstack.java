package helpers;

import config.credentials.CredentialsConfig;
import config.mobile.RemoveAndroidConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.UploadAppResponseModel;
import models.UploadedAppsListResponseModel;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Browserstack {

    private static final CredentialsConfig credentialsConfig =
            ConfigFactory.create(CredentialsConfig.class, System.getProperties());

    public static String videoUrl(String sessionId) {
        String url = String.format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .auth().basic(credentialsConfig.getBrowserstackUser(), credentialsConfig.getBrowserstackKey())
                .get(url)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }

/*
    // Загружает приложение на БС
    public UploadAppResponseModel uploadAppToBrowserstack(String appName) {

        //String path = "src/test/resources/apps/com.todoist-11342.apk";
        String path = String.format("src/test/resources/apps/%s", appName);
        File file = new File(path);

        return given()
                .auth().preemptive().basic(credentialsConfig.getBrowserstackUser(), credentialsConfig.getBrowserstackKey())
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .post("https://api-cloud.browserstack.com/app-automate/upload")
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(UploadAppResponseModel.class);
    }

    // Проверяет загружено ли уже приложение
    public String checkUploadedAppsList(String appName) {

        //deleteApp("6c06179b3d81a3e0d5a9fb2488e2ef097fc5bac6");
        //deleteApp("9e392d84bff7995744f16010e6bf9bb5cd8cd19d");
        //deleteApp("da02849da3fd88922ff6cde24571671034592527");

        String response = given()
                .auth().preemptive().basic(credentialsConfig.getBrowserstackUser(), credentialsConfig.getBrowserstackKey())
                .when()
                .get("https://api-cloud.browserstack.com/app-automate/recent_apps")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .asString();

        if (!response.contains("No results found")) {   // TODO : перепроверить
            List<UploadedAppsListResponseModel> jResponse =
                    new JsonPath(response).getList(".", UploadedAppsListResponseModel.class);

            for (UploadedAppsListResponseModel app : jResponse) {
                //if (app.getAppName().equals("com.todoist-11342.apk")) {
                if (app.getAppName().equals(appName)) {
                    return app.getAppUrl();
                }
            }
        }

        return uploadAppToBrowserstack(appName).getAppUrl();
    }*/



    // Загружает приложение на БС
    private String uploadApp(String appName) {

        //String path = "src/test/resources/apps/com.todoist-11342.apk";
        String path = String.format("src/test/resources/apps/%s", appName);
        File file = new File(path);

        UploadAppResponseModel response = given()
                .auth().preemptive().basic(credentialsConfig.getBrowserstackUser(), credentialsConfig.getBrowserstackKey())
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .post("https://api-cloud.browserstack.com/app-automate/upload")
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(UploadAppResponseModel.class);

        return response.getAppUrl();
    }

    private String checkUploadedApp(String appName) {

        String response = given()
                .auth().preemptive().basic(credentialsConfig.getBrowserstackUser(), credentialsConfig.getBrowserstackKey())
                .when()
                .get("https://api-cloud.browserstack.com/app-automate/recent_apps")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .asString();

        if (!response.contains("No results found")) {
            List<UploadedAppsListResponseModel> appList =
                    new JsonPath(response).getList(".", UploadedAppsListResponseModel.class);

            for (UploadedAppsListResponseModel app : appList) {
                //if (app.getAppName().equals("com.todoist-11342.apk")) {
                if (app.getAppName().equals(appName)) {
                    return app.getAppUrl();
                }
            }
        }

        return "";
    }


    // Получаем app
    public String getAppUrl(String appName) {

        var appUrl = checkUploadedApp(appName);

        if (appUrl.isEmpty()) {
            appUrl = uploadApp(appName);
        }

        return appUrl;
    }

    private void deleteApp(String appId) {

        given()
                .auth().preemptive().basic(credentialsConfig.getBrowserstackUser(), credentialsConfig.getBrowserstackKey())
                .when()
                .delete("https://api-cloud.browserstack.com/app-automate/app/delete/" + appId)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .asString();
    }
}