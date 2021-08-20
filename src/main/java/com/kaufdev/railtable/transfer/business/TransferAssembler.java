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
    private final static BigDecimal SECOND_CLASS_FACTOR = BigDecimal.valueOf(0.5);
    private final StationAssembler stationAssembler;

    public TransferAssembler(StationAssembler stationAssembler) {
        this.stationAssembler = stationAssembler;
    }

    public TransferDto assemble(Transfer transfer, String startStationAcronym, String endStationAcronym){
        Optional<Section> startSectionOptional = transfer.getSections().stream()
                .filter(section -> section.hasStartStationAcronym(startStationAcronym)).findFirst();
        Optional<Section> endSectionOptional = transfer.getSections().stream()
                .filter(section -> section.hasEndStationAcronym(endStationAcronym)).findFirst();

        if(startSectionOptional.isPresent() && endSectionOptional.isPresent()){
            final Section startSection = startSectionOptional.get();
            final Section endSection = endSectionOptional.get();
            int length = 0;
            Section currentSection = startSection;

            while(currentSection != null){
                length = length + currentSection.getLength();
                if(currentSection.equals(endSection)) break;
                currentSection = currentSection.getNextSection();
            }

            BigDecimal firstClassCost = transfer.getLengthCostFactor().multiply(BigDecimal.valueOf(length));
            BigDecimal secondClassCost = SECOND_CLASS_FACTOR.multiply(firstClassCost);

            return new TransferDto(startSection.getStartTime(),
                    endSection.getEndTime(),
                    stationAssembler.assemble(startSection.getStartStation()),
                    stationAssembler.assemble(endSection.getEndStation()),
                    transfer.getOperator(),
                    firstClassCost,
                    secondClassCost,
                    emptyList());
        }

        return null;
    }
}
