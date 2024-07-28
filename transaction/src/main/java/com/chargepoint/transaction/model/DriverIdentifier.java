package com.chargepoint.transaction.model;

import jakarta.validation.constraints.NotNull;

public class DriverIdentifier {

    @NotNull(message = "id cannot be null") 
    private String id;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
