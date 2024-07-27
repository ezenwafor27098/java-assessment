package com.chargepoint.transaction.model;

public class AuthenticationResponse {
    private String stationUuid;
    private String authenticationStatus;

    public AuthenticationResponse() {}

    public AuthenticationResponse(String stationUuid, String authenticationStatus) {
        this.stationUuid = stationUuid;
        this.authenticationStatus = authenticationStatus;
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

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
