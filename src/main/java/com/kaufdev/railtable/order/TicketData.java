package com.kaufdev.railtable.order;

public class TicketData {
    private final String identifier;
    private final String email;

    public TicketData(String identifier, String email) {
        this.identifier = identifier;
        this.email = email;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "TicketData{" +
                "identifer='" + identifier + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
