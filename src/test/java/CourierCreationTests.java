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

public class CourierCreationTests {
    private final CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
    }

    @Test
    @DisplayName("Successful creation of courier")
    public void createNewCourier() {
        CourierCreationRequestData json = Courier.createValidCourier();
        Response response = courierClient.create(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Not allowed to create two identical couriers")
    public void twinCouriersCreationIsUnavailable() {
        CourierCreationRequestData json = Courier.createValidCourier();
        Response firstResponse = courierClient.create(json);
        firstResponse.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
        Response secondResponse = courierClient.create(json);
        secondResponse.then().assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
        ;
    }

    @Test
    @DisplayName("Not allowed to create courier without login")
    public void couriersCreationWithoutLoginIsUnavailable() {
        CourierCreationRequestData json = Courier.genericCreateCourierWithoutLogin();
        Response response = courierClient.create(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Not allowed to create courier without password")
    public void couriersCreationWithoutPasswordIsUnavailable() {
        CourierCreationRequestData json = Courier.genericCreateCourierWithoutPassword();
        Response response = courierClient.create(json);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourierAfterCreation() {
        // 1 шаг - вытащить айди созданного курьера, дернув ручку Логин
        CourierLoginRequestData courierLoginRequestJson = Courier.genericCourierWithValidCredentials();
        Response response = courierClient.login(courierLoginRequestJson);
        CourierLoginResponseData responseData = response.as(CourierLoginResponseData.class);
        // 2 шаг - удалить курьера по айди
        courierClient.delete(responseData.getId());
    }
}

