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

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginPositiveTests {

    private String courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
        CourierCreationRequestData json = Courier.createValidCourier();
        Response firstResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
    }
    @Test
    @DisplayName("Successful login of courier")
    public void loginCourier(){
        CourierLoginRequestData courierLoginDataJson = Courier.genericCourierWithValidCredentials();
        Response secondResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierLoginDataJson)
                        .when()
                        .post("/api/v1/courier/login");
        secondResponse.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", notNullValue());
        courierId = secondResponse.as(CourierLoginResponseData.class).getId();
    }
    @After
    public void deleteCourierAfterCreation() {
    given()
                .delete("/api/v1/courier/" + courierId);
}
}
