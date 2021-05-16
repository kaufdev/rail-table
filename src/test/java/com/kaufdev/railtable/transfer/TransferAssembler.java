package com.kaufdev.railtable.transfer;

import java.math.BigDecimal;
import java.util.Optional;

public class TransferAssembler {
    private final static BigDecimal SECOND_CLASS_FACTOR = BigDecimal.valueOf(0.5);

    public TransferDto assemble(Transfer transfer, String startStationAcronym, String endStationAcronym){
        Optional<Section> startSectionOptional = transfer.getSections().stream().filter(section -> section.hasStartStationAcronym(startStationAcronym)).findFirst();
        Optional<Section> endSectionOptional = transfer.getSections().stream().filter(section -> section.hasEndStationAcronym(endStationAcronym)).findFirst();

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
                    startSection.getStartStation().getAcronym(),
                    endSection.getEndStation().getAcronym(),
                    startSection.getStartStation().getName(),
                    endSection.getEndStation().getName(),
                    transfer.getOperator(),
                    firstClassCost,
                    secondClassCost);
        }

        return null;
    }
}
