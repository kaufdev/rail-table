package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Transfer;

import java.math.BigDecimal;

public class SectionTicketCost {
    private final static BigDecimal SECOND_CLASS_FACTOR = BigDecimal.valueOf(0.5);
    private final BigDecimal firstClassCost;
    private final BigDecimal secondClassCost;

    public SectionTicketCost(Section startingSection, Section endingSection) {
        final Transfer transfer = startingSection.getTransfer();
        int length = 0;
        Section currentSection = startingSection;

        while(currentSection != null){
            length = length + currentSection.getLength();
            if(currentSection.equals(endingSection)) break;
            currentSection = currentSection.getNextSection();
        }

        firstClassCost = transfer.getLengthCostFactor().multiply(BigDecimal.valueOf(length));
        secondClassCost = SECOND_CLASS_FACTOR.multiply(firstClassCost);
    }

    public BigDecimal getFirstClassCost(){
        return this.firstClassCost;
    }

    public BigDecimal getSecondClassCost(){
        return this.secondClassCost;
    }
}
