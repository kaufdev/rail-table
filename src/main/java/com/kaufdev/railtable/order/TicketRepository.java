package com.kaufdev.railtable.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket save(Ticket ticket);
    Optional<Ticket> findByUuidAndEmail(String uuid, String email);
}
