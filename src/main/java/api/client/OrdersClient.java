package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static String createOrGettingListUrl = "/api/v1/orders";

    @Step("API to getting a list of orders")
    public Response gettingList() {
        return given()
                .get(createOrGettingListUrl);
    }

    @Step("API to create an order")
    public Response create(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(createOrGettingListUrl);
    }
}
