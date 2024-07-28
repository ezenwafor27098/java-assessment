package com.chargepoint.authentication.model;

public class AuthenticationResponse {
    private String requestUuid;
    private String authenticationStatus;

    public AuthenticationResponse() {}

    public AuthenticationResponse(String requestUuid, String authenticationStatus) {
        this.requestUuid = requestUuid;
        this.authenticationStatus = authenticationStatus;
    }

    public String getRequestUuid() {
        return requestUuid;
    }

    public void setRequestUuid(String requestUuid) {
        this.requestUuid = requestUuid;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
