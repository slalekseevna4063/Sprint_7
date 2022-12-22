package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static String createCourierUrl = "/api/v1/courier";
    private static String loginCourierUrl = "/api/v1/courier/login";
    private static String deleteCourierUrl = "/api/v1/courier/";

    @Step("API to create a courier")
    public Response create(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(createCourierUrl);
    }

    @Step("API to login a courier")
    public Response login(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(loginCourierUrl);
    }

    @Step("API to delete a courier")
    public Response delete(String id) {
        return given()
                .delete(deleteCourierUrl + id);
    }
}