import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import settings.UrlSettings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GettingOrdersListTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
    }

    @Test
    @DisplayName("A list of orders is returned in the response body and it is not null")
    public void ordersListIsNotNull() {
        given()
                .get("/api/v1/orders")
                .then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);
    }
}
