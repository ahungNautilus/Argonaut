package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestMakers {

    public static Response makeGetRequest(String url) {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(url)
                .then()
                .extract().response();
        return response;
    }

    public static Response makePostRequest(String url, String bodyContent) {
        Response response = given()
                .when()
                .body(bodyContent)
                .post(url)
                .then()
                .extract().response();
        return response;
    }
}
