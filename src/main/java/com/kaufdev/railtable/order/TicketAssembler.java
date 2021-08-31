package com.kaufdev.railtable.order;

import java.util.List;
import java.util.stream.Collectors;

public class TicketAssembler {
    static TicketOrderedDto assemble(Ticket ticket){
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
                .setTicketType(ticket.getTicketType())
                .setIdentifier(ticket.getUuid())
                .build();
    }
}
