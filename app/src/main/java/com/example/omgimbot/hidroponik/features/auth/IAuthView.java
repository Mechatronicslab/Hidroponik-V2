package com.example.omgimbot.hidroponik.features.auth;

import com.example.omgimbot.hidroponik.features.auth.model.LoginResponse;

public interface IAuthView {
    void initViews();

    void showSignupForm();

    void showSigninForm();

    void showLoadingIndicator();

    void hideLoadingIndicator();

    boolean isNameLayoutReady();

    void setNameLayoutVisible(boolean visible);

    boolean isVerificationLayoutReady();

    void setVerificationLayoutVisible(boolean visible);

    boolean isPasswordLayoutReady();

    void setPasswordLayoutVisible(boolean visible);

    void showSignupButton();

    void signIn();

    void signUp();

    void onSigninSuccess(LoginResponse response);

    void onSignupSuccess(/*SignupResponse response*/);

    void onRequestFailed(String rm);

    void onNetworkFailed(String cause);

    void goToDashboard();
}
