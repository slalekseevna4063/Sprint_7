import api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import settings.UrlSettings;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GettingOrdersListTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
    }

    @Test
    @DisplayName("A list of orders is returned in the response body and it is not null")
    public void ordersListIsNotNull() {
        OrdersClient ordersClient = new OrdersClient();
        Response response = ordersClient.gettingList();
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);
    }
}
