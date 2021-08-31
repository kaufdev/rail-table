package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Transfer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SectionCalculator {
    private final static BigDecimal SECOND_CLASS_FACTOR = BigDecimal.valueOf(0.5);
    private final static int MAXIMUM_CAR_CAPACITY = 60;
    private final BigDecimal firstClassCost;
    private final BigDecimal secondClassCost;
    private final Set<Long> sectionsIds;
    private final int smallestSeatCapacityForSecondClass;
    private final int smallestAvailableSeatNumberForSecondClass;
    private final int smallestSeatCapacityForFirstClass;
    private final int smallestAvailableSeatNumberForFirstClass;

    public SectionCalculator(Section startingSection, Section endingSection) {
        final Transfer transfer = startingSection.getTransfer();
        int length = 0;
        Section currentSection = startingSection;
        Set<Long> sectionIdsUnderConstructions = new HashSet<>();
        int currentAllSeatsForSecondClass = MAXIMUM_CAR_CAPACITY;
        int currentAvailableSeatsForSecondClass = MAXIMUM_CAR_CAPACITY;
        int currentAllSeatsForFirstClass = MAXIMUM_CAR_CAPACITY;
        int currentAvailableSeatsForFirstClass = MAXIMUM_CAR_CAPACITY;

        while(currentSection != null){
            length = length + currentSection.getLength();

            if(currentSection.getAllSeatsForSecondClass() < currentAllSeatsForSecondClass){
                currentAllSeatsForSecondClass = currentSection.getAllSeatsForSecondClass();
            }

            if(currentSection.getAvailableSeatsForSecondClass() < currentAvailableSeatsForSecondClass){
                currentAvailableSeatsForSecondClass = currentSection.getAvailableSeatsForSecondClass();
            }

            if(currentSection.getAllSeatsForFirstClass() < currentAllSeatsForFirstClass){
                currentAllSeatsForFirstClass = currentSection.getAllSeatsForFirstClass();
            }

            if(currentSection.getAvailableSeatsForFirstClass() < currentAvailableSeatsForFirstClass){
                currentAvailableSeatsForFirstClass = currentSection.getAvailableSeatsForFirstClass();
            }

            sectionIdsUnderConstructions.add(currentSection.getId());
            if(currentSection.equals(endingSection)) break;
            currentSection = currentSection.getNextSection();
        }

        firstClassCost = transfer.getLengthCostFactor().multiply(BigDecimal.valueOf(length));
        secondClassCost = SECOND_CLASS_FACTOR.multiply(firstClassCost);
        sectionsIds = sectionIdsUnderConstructions;
        smallestAvailableSeatNumberForSecondClass = currentAvailableSeatsForSecondClass;
        smallestSeatCapacityForSecondClass = currentAllSeatsForSecondClass;
        smallestAvailableSeatNumberForFirstClass = currentAvailableSeatsForFirstClass;
        smallestSeatCapacityForFirstClass = currentAllSeatsForFirstClass;
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

    public int getAvailableSeatsForSecondClass(){
        return this.smallestAvailableSeatNumberForSecondClass;
    }

    public int getAllSeatsForSecondClass(){
        return this.smallestSeatCapacityForSecondClass;
    }

    public int getAvailableSeatsForFirstClass(){
        return this.smallestAvailableSeatNumberForFirstClass;
    }

    public int getAllSeatsForFirstClass(){
        return this.smallestSeatCapacityForFirstClass;
    }
}
