package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Transfer;
import org.springframework.stereotype.Component;

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
            final SectionCalculator sectionCalculator = new SectionCalculator(startSection, endSection);

            return new TransferDto(startSection.getStartTime(),
                    endSection.getEndTime(),
                    StationAssembler.assemble(startSection.getStartStation()),
                    StationAssembler.assemble(endSection.getEndStation()),
                    transfer.getOperator(),
                    sectionCalculator.getFirstClassCost(),
                    sectionCalculator.getSecondClassCost(),
                    emptyList(),
                    sectionCalculator.getSectionsIds(),
                    sectionCalculator.getAllSeats(),
                    sectionCalculator.getAvailableSeats());
        }

        return null;
    }
}
