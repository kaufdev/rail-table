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
        final List<TicketTransferDto> ticketTransfers = ticket.getBoughtSections().stream()
                .map(section -> new TicketTransferDto(section.getStartStationName(),
                        section.getEndStationName(),
                        section.getStartTime(),
                        section.getEndTime(),
                        section.getOperator()))
                .sorted()
                .collect(Collectors.toList());

        return new TicketOrderedDto.Builder()
                .setFirstName(ticket.getFirstName())
                .setLastName(ticket.getLastName())
                .setEmail(ticket.getEmail())
                .setTransfers(ticketTransfers)
                .setPrice(ticket.getPrice())
                .build();
    }
}
