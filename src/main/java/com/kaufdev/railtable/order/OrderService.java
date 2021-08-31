package com.kaufdev.railtable.order;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final TicketRepository ticketRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public OrderService(TicketRepository ticketRepository, SectionRepository sectionRepository) {
        this.ticketRepository = ticketRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public Ticket orderTicket(TicketOrderDto dto) {
        Set<Section> ticketSections = dto.getSectionsIds().stream()
                .map(sectionRepository::findById).map(Optional::get).collect(Collectors.toSet());

        validateSeats(ticketSections, dto.getTicketType());

        if ("First Class".equals(dto.getTicketType())) {
            ticketSections.forEach(Section::takeSeatForFirstClass);
        } else {
            ticketSections.forEach(Section::takeSeatForSecondClass);
        }

        Ticket ticketToCreate = new Ticket(ticketSections, dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPrice(), dto.getTicketType());

        return ticketRepository.save(ticketToCreate);
    }

    private void validateSeats(Set<Section> sections, String ticketType) {
        final Set<Section> sectionWithoutAvailableSeatsForSecondClass = getSectionWithoutSeats(sections, ticketType);
        handleSectionWithoutAvailableSeats(sectionWithoutAvailableSeatsForSecondClass);
    }

    private Set<Section> getSectionWithoutSeats(Set<Section> sections, String ticketType) {
        if ("First Class".equals(ticketType)) {
            return sections.stream()
                    .filter(section -> !section.isEmptySeatPossibleForFirstClass())
                    .collect(Collectors.toSet());
        } else {
            return sections.stream()
                    .filter(section -> !section.isEmptySeatPossibleForSecondClass())
                    .collect(Collectors.toSet());
        }
    }

    private void handleSectionWithoutAvailableSeats(Set<Section> sectionWithoutAvailableSeatsForSecondClass) {
        if (!sectionWithoutAvailableSeatsForSecondClass.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("No available seats ");
            List<String> betweens = sectionWithoutAvailableSeatsForSecondClass.stream()
                    .map(section -> "between " + section.getStartStationName() + " and " + section.getEndStationName())
                    .collect(Collectors.toList());
            sb.append(String.join(" and ", betweens));
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
