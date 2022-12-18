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
import static org.hamcrest.Matchers.*;

public class CourierCreationTests {

        @Before
        public void setUp() {
        RestAssured.baseURI = UrlSettings.SERVICE_URL;
        }
        @Test
        @DisplayName("Successful creation of courier")
        public void createNewCourier(){
            CourierCreationRequestData json = Courier.createValidCourier();
            Response response =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(json)
                            .when()
                            .post("/api/v1/courier");
            response.then().assertThat()
                    .statusCode(HttpStatus.SC_CREATED)
                    .and()
                    .body("ok", equalTo(true));
        }
        @Test
        @DisplayName("Not allowed to create two identical couriers")
        public void twinCouriersCreationIsUnavailable(){
            CourierCreationRequestData json = Courier.createValidCourier();
            Response firstResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
            firstResponse.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
            Response secondResponse =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
            secondResponse.then().assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));;
    }
        @Test
        @DisplayName("Not allowed to create courier without login")
        public void couriersCreationWithoutLoginIsUnavailable() {
            CourierCreationRequestData json = Courier.genericCreateCourierWithoutLogin();
            Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
            response.then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
        @Test
        @DisplayName("Not allowed to create courier without password")
        public void couriersCreationWithoutPasswordIsUnavailable() {
            CourierCreationRequestData json = Courier.genericCreateCourierWithoutPassword();
            Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
            response.then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
        @After
        public void deleteCourierAfterCreation() {
            // 1 шаг - вытащить айди созданного курьера, дернув ручку Логин
            CourierLoginRequestData courierLoginRequestJson = Courier.genericCourierWithValidCredentials();
        CourierLoginResponseData response =
                given()
                        .header("Content-type", "application/json")
                        .body(courierLoginRequestJson)
                        .post("/api/v1/courier/login")
                        .as(CourierLoginResponseData.class);
           // 2 шаг - удалить курьера по айди
                given()
                    .delete("/api/v1/courier/" + response.getId());
        }
}

