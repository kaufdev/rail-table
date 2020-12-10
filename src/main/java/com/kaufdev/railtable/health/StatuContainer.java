package com.kaufdev.railtable.health;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class StatuContainer {
    private final String statusCode;

    StatuContainer(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
