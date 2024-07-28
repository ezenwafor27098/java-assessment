package com.chargepoint.transaction.model;

public class APIResponse {
    private String authenticationStatus;

    public APIResponse(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
