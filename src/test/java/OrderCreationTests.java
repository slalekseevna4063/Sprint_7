import datapojos.OrderCreationRequestData;
import datapojos.OrderCreationResponseData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import settings.UrlSettings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class OrderCreationTests {
    private final String[] color;

    public OrderCreationTests(String[] color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{}}
        };
}
    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;}

    @Test
    @DisplayName("Successful order creation with different color variations (color is optional field)")
    public void SuccessfulOrderCreation() {
        OrderCreationRequestData orderCreationDataPojo = new OrderCreationRequestData("Лариса", "Сиротина", "Ермолаевская,22", "4", "+7 800 355 35 35", 5, "2020-06-06", "Текст комментария", color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(orderCreationDataPojo)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
        OrderCreationResponseData responseOrderCreationPojo = response.as(OrderCreationResponseData.class);
                assertThat(responseOrderCreationPojo.getTrack(), notNullValue());
    }
}
