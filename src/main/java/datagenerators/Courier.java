package datagenerators;

import datapojos.CourierCreationRequestData;
import datapojos.CourierLoginRequestData;

public class Courier {
    public static CourierCreationRequestData createValidCourier() {
        return new CourierCreationRequestData("flash", "222222", "Курьерский");
    }

    public static CourierCreationRequestData genericCreateCourierWithoutLogin() {
        return new CourierCreationRequestData(null, "222222", "Курьерский");
    }

    public static CourierCreationRequestData genericCreateCourierWithoutPassword() {
        return new CourierCreationRequestData(null, "222222", "Курьерский");
    }

    public static CourierLoginRequestData genericCourierWithValidCredentials() {
        return new CourierLoginRequestData("flash", "222222");
    }

    public static CourierLoginRequestData genericCourierWithoutLogin() {
        return new CourierLoginRequestData(null, "222222");
    }

    public static CourierLoginRequestData genericCourierWithoutPassword() {
        return new CourierLoginRequestData("flash", null);
    }

    public static CourierLoginRequestData genericCourierWithWrongLogin() {
        return new CourierLoginRequestData("lash", "222222");
    }

    public static CourierLoginRequestData genericCourierWithWrongPassword() {
        return new CourierLoginRequestData("flash", "2222");
    }

}
