package data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getAnotherAuthInfo() {
        return new AuthInfo("vasya", "123qwerty");
    }
}