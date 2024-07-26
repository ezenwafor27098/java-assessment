package com.chargepoint.authentication.model;

public class AuthenticationResponse {
    private String stationUuid;
    private String authenticationStatus;

    public AuthenticationResponse(String stationUuid, String authStatus) {
        this.stationUuid = stationUuid;
        this.authenticationStatus = authStatus;
    }

    public String getStationUuid() {
        return stationUuid;
    }

    public void setStationUuid(String stationUuid) {
        this.stationUuid = stationUuid;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authStatus) {
        this.authenticationStatus = authStatus;
    }
}
