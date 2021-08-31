package com.kaufdev.railtable.transfer.business;

import com.google.common.collect.ArrayListMultimap;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.infrastracture.InterchangeTransferDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class TransferByInterchanges {
    private final List<InterchangeTransferDto> interchanges;

    public TransferByInterchanges(List<Section> sectionPath) {
        if(sectionPath.isEmpty()){
            interchanges = Collections.emptyList();
        } else {
            final List<Long> pathByTransferIds = sectionPath.stream()
                    .map(Section::getTransferId)
                    .distinct()
                    .collect(Collectors.toList());

            ArrayListMultimap<Long, Section> multiMap = ArrayListMultimap.create();
            sectionPath.forEach(section -> multiMap.put(section.getTransferId(),section));

            interchanges = pathByTransferIds.stream()
                    .map(multiMap::get)
                    .map(this::transformSectionsToInterchange)
                    .collect(Collectors.toList());
        }
    }

    public Set<Long> getAllSectionIds(){
        return this.interchanges.stream()
                .map(InterchangeTransferDto::getSectionsIds)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private InterchangeTransferDto transformSectionsToInterchange(List<Section> sections){
        if(sections.isEmpty()){
            throw new IllegalArgumentException("Somehow transfer has non sections - it's db data error");
        }
        Section startingSection = sections.get(0);
        Section endingSection = sections.get(sections.size() - 1);

        SectionCalculator sectionCalculator = new SectionCalculator(startingSection, endingSection);

        return new InterchangeTransferDto(startingSection.getStartTime(),
                endingSection.getEndTime(),
                startingSection.getStartStationName(),
                endingSection.getEndStationName(),
                startingSection.getOperator(),
                sectionCalculator.getFirstClassCost(),
                sectionCalculator.getSecondClassCost(),
                sectionCalculator.getSectionsIds(),
                sectionCalculator.getAllSeatsForSecondClass(),
                sectionCalculator.getAvailableSeatsForSecondClass());
    }


    public List<InterchangeTransferDto> getInterchanges(){return this.interchanges;}

    public String getOperators(){
        List<String> operators = this.interchanges.stream().map(InterchangeTransferDto::getOperator).collect(Collectors.toList());
        return String.join("+",operators);
    }

    public BigDecimal getTotalFirstClassCost(){
        return this.interchanges.stream().map(InterchangeTransferDto::getFirstClassCost).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal getTotalSecondClassCost(){
        return this.interchanges.stream().map(InterchangeTransferDto::getFirstClassCost).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public int getSmallestAllSeatsForSecondClass(){
        return  this.interchanges.stream().map(InterchangeTransferDto::getAllSeatsForSecondClass).min(Integer::compareTo).orElse(0);
    }

    public int getSmallestAvailableSeatsForSecondClass(){
        return this.interchanges.stream().map(InterchangeTransferDto::getAvailableSeatsForSecondClass).min(Integer::compareTo).orElse(0);
    }
}
