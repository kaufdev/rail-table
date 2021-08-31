package com.kaufdev.railtable.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/ticket")
    public TicketOrderedDto orderTicket(@RequestBody TicketOrderDto ticketOrderDto) {
        final Ticket ticket = orderService.orderTicket(ticketOrderDto);
        return TicketAssembler.assemble(ticket);
    }
}
