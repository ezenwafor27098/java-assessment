package com.chargepoint.transaction.model;

public class AuthenticationRequest {
    private String requestUuid;
    private String authenticationToken;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String requestUuid, String authenticationToken) {
        this.requestUuid = requestUuid;
        this.authenticationToken = authenticationToken;
    }

    public String getRequestUuid() {
        return requestUuid;
    }

    public void setRequestUuid(String requestUuid) {
        this.requestUuid = requestUuid;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
