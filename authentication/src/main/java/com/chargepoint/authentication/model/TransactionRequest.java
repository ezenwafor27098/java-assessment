package com.chargepoint.authentication.model;

public class TransactionRequest {
    private String stationUuid;
    private DriverIdentifier driverIdentifier;

    public String getStationUuid() {
        return stationUuid;
    }

    public void setStationUuid(String stationUuid) {
        this.stationUuid = stationUuid;
    }

    public DriverIdentifier getDriverIdentifier() {
        return driverIdentifier;
    }

    public void setDriverIdentifier(DriverIdentifier driverIdentifier) {
        this.driverIdentifier = driverIdentifier;
    }
}
