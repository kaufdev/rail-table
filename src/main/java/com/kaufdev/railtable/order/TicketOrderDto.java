package com.kaufdev.railtable.order;

import com.sun.istack.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class TicketOrderDto {
    @NotNull
    private List<Long> sectionsIds;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String ticketType;


    public TicketOrderDto() {
    }

    @Override
    public String toString() {
        return "TicketOrderDto{" +
                "sectionsIds=" + sectionsIds +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketOrderDto that = (TicketOrderDto) o;
        return Objects.equals(sectionsIds, that.sectionsIds) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionsIds, firstName, lastName, email, price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Long> getSectionsIds() {
        return sectionsIds;
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

    public String getTicketType() {
        return ticketType;
    }
}
