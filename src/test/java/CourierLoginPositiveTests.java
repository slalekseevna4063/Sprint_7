import api.client.CourierClient;
import datagenerators.Courier;
import datapojos.CourierCreationRequestData;
import datapojos.CourierLoginRequestData;
import datapojos.CourierLoginResponseData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import settings.UrlSettings;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginPositiveTests {
    private final CourierClient courierClient = new CourierClient();

    private String courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
        CourierCreationRequestData json = Courier.createValidCourier();
        Response response = courierClient.create(json);
    }

    @Test
    @DisplayName("Successful login of courier")
    public void loginCourier() {
        CourierLoginRequestData courierLoginDataJson = Courier.genericCourierWithValidCredentials();
        Response response = courierClient.login(courierLoginDataJson);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", notNullValue());
        courierId = response.as(CourierLoginResponseData.class).getId();
    }

    @After
    public void deleteCourierAfterCreation() {
        courierClient.delete(courierId);
    }
}