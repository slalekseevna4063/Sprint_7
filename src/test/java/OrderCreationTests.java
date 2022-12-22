import api.client.OrdersClient;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class OrderCreationTests {
    private final OrdersClient ordersClient = new OrdersClient();
    private final List<String> color;

    public OrderCreationTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Color: {0}")
    public static Object[][] data() {
        return new Object[][]{
                {List.of("BLACK", "GRAY")},
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
    }

    @Test
    @DisplayName("Successful order creation with different color variations (color is optional field)")
    public void SuccessfulOrderCreation() {
        OrderCreationRequestData orderCreationDataPojo = new OrderCreationRequestData("Лариса", "Сиротина", "Ермолаевская,22", "4", "+7 800 355 35 35", 5, "2020-06-06", "Текст комментария", color);
        Response response = ordersClient.create(orderCreationDataPojo);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
        OrderCreationResponseData responseOrderCreationPojo = response.as(OrderCreationResponseData.class);
        assertThat(responseOrderCreationPojo.getTrack(), notNullValue());
    }
}
