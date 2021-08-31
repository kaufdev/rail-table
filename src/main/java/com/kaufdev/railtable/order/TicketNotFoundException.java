package com.kaufdev.railtable.order;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String s) {
        super(s);
    }
}
