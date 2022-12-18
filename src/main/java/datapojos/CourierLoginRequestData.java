package datapojos;

public class CourierLoginRequestData {
    private String login;
    private String password;

    public CourierLoginRequestData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLoginRequestData() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


