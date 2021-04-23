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
                .header("user_id", "user4@mail.com")
                .header("Authorization", "jwtExample")
                .when()
                .body(bodyContent)
                .post(url)
                .then()
                .extract().response();
        return response;
    }
}
