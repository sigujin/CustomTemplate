package com.vito.base.action;

public class LoginSuccessfullyAction {
    private static Action onLoginSuccessfully = null;

    public static Action getOnLoginSuccessfully() {
        return onLoginSuccessfully;
    }

    public static void setOnLoginSuccessfully(Action onLoginSuccessfully) {
        LoginSuccessfullyAction.onLoginSuccessfully = onLoginSuccessfully;
    }

}
