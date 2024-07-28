package com.chargepoint.transaction.model;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotNull;

public class TransactionRequest {

    @NotNull(message = "stationUuid cannot be null")
    @UUID(message = "stationUuid must be a valid UUID")
    private String stationUuid;

    @NotNull(message = "driverIdentifier cannot be null")
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
