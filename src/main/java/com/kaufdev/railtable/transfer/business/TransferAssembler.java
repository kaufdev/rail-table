package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.infrastracture.InterchangeTransferDto;
import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Transfer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.*;

@Component
public class TransferAssembler {
    public TransferDto assemble(Transfer transfer, String startStationAcronym, String endStationAcronym){
        Optional<Section> startSectionOptional = transfer.getSections().stream()
                .filter(section -> section.hasStartStationAcronym(startStationAcronym)).findFirst();
        Optional<Section> endSectionOptional = transfer.getSections().stream()
                .filter(section -> section.hasEndStationAcronym(endStationAcronym)).findFirst();

        if(startSectionOptional.isPresent() && endSectionOptional.isPresent()){
            final Section startSection = startSectionOptional.get();
            final Section endSection = endSectionOptional.get();
            final SectionTicketCost sectionTicketCost = new SectionTicketCost(startSection, endSection);

            return new TransferDto(startSection.getStartTime(),
                    endSection.getEndTime(),
                    StationAssembler.assemble(startSection.getStartStation()),
                    StationAssembler.assemble(endSection.getEndStation()),
                    transfer.getOperator(),
                    sectionTicketCost.getFirstClassCost(),
                    sectionTicketCost.getSecondClassCost(),
                    emptyList());
        }

        return null;
    }
}
