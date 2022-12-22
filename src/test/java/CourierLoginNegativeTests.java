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

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginNegativeTests {

    private final CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
        // создала нового курьера
        CourierCreationRequestData courierCreationRequestJson = Courier.createValidCourier();
        Response response = courierClient.create(courierCreationRequestJson);
    }

    @Test
    @DisplayName("Not allowed to login without login")
    public void couriersLoginWithoutLoginIsUnavailable() {
        CourierLoginRequestData json = Courier.genericCourierWithoutLogin();
        Response response = courierClient.login(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Not allowed to login without password")
    public void couriersLoginWithoutPasswordIsUnavailable() {
        CourierLoginRequestData json = Courier.genericCourierWithoutPassword();
        Response response = courierClient.login(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Not allowed to login with wrong login")
    public void couriersLoginWithWrongLoginIsUnavailable() {
        CourierLoginRequestData json = Courier.genericCourierWithWrongLogin();
        Response response = courierClient.login(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Not allowed to login with wrong password")
    public void couriersLoginWithWrongPasswordIsUnavailable() {
        CourierLoginRequestData json = Courier.genericCourierWithWrongPassword();
        Response response = courierClient.login(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourierAfterCreation() {
        // попытка уже ВАЛИДНОГО логина и вытаскиваю айди курьера из ответа чтобы удалить курьера
        CourierLoginRequestData json = Courier.genericCourierWithValidCredentials();
        Response response = courierClient.login(json);
        CourierLoginResponseData responseData = response.as(CourierLoginResponseData.class);
        courierClient.delete(responseData.getId());
    }
}
