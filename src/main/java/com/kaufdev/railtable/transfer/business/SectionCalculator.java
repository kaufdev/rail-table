package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Transfer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SectionCalculator {
    private final static BigDecimal SECOND_CLASS_FACTOR = BigDecimal.valueOf(0.5);
    private final BigDecimal firstClassCost;
    private final BigDecimal secondClassCost;
    private final Set<Long> sectionsIds;

    public SectionCalculator(Section startingSection, Section endingSection) {
        final Transfer transfer = startingSection.getTransfer();
        int length = 0;
        Section currentSection = startingSection;
        Set<Long> sectionIdsUnderConstructions = new HashSet<>();

        while(currentSection != null){
            length = length + currentSection.getLength();
            sectionIdsUnderConstructions.add(currentSection.getId());
            if(currentSection.equals(endingSection)) break;
            currentSection = currentSection.getNextSection();
        }

        firstClassCost = transfer.getLengthCostFactor().multiply(BigDecimal.valueOf(length));
        secondClassCost = SECOND_CLASS_FACTOR.multiply(firstClassCost);
        sectionsIds = sectionIdsUnderConstructions;
    }

    public BigDecimal getFirstClassCost(){
        return this.firstClassCost;
    }

    public BigDecimal getSecondClassCost(){
        return this.secondClassCost;
    }

    public Set<Long> getSectionsIds(){
        return this.sectionsIds;
    }
}
