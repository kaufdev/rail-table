package com.kaufdev.railtable.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/search")
    public TicketOrderedDto getTicket(@RequestBody TicketData ticketData) {
        return ticketRepository.findByUuidAndEmail(ticketData.getIdentifier(), ticketData.getEmail())
                .map(TicketAssembler::assemble)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
    }
}
