package com.kaufdev.railtable.order;

import java.math.BigDecimal;
import java.util.List;

public class TicketOrderedDto {
    private final List<TicketTransferDto> transfers;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Long price;
    private final String ticketType;
    private final String identifier;


    private TicketOrderedDto(List<TicketTransferDto> transfers,
                             String firstName,
                             String lastName,
                             String email,
                             Long price,
                             String ticketType,
                             String identifier) {
        this.transfers = transfers;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.price = price;
        this.ticketType =ticketType;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTicketType() {
        return ticketType;
    }

    public Long getPrice() {
        return price;
    }

    public List<TicketTransferDto> getTransfers() {
        return transfers;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    static class Builder {
        private List<TicketTransferDto> transfers;
        private String firstName;
        private String lastName;
        private String email;
        private Long price;
        private String ticketType;
        private String identifier;

        public Builder setTransfers(List<TicketTransferDto> transfers) {
            this.transfers = transfers;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPrice(BigDecimal price){
            this.price = price.longValue();
            return this;
        }

        public Builder setTicketType(String ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Builder setIdentifier(String uuid) {
            this.identifier = uuid;
            return this;
        }

        public TicketOrderedDto build(){
            return new TicketOrderedDto(transfers,firstName,lastName,email, price, ticketType, identifier);
        }
    }
}
