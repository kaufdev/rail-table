package com.kaufdev.railtable.order;

import com.kaufdev.railtable.transfer.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket save(Ticket ticket);
}
