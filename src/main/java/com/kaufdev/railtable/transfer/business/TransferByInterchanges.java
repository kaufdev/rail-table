package com.kaufdev.railtable.transfer.business;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Station;
import com.kaufdev.railtable.transfer.infrastracture.InterchangeTransferDto;
import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private InterchangeTransferDto transformSectionsToInterchange(List<Section> sections){
        if(sections.isEmpty()){
            throw new IllegalArgumentException("Somehow transfer has non sections - it's db data error");
        }
        Section startingSection = sections.get(0);
        Section endingSection = sections.get(sections.size() - 1);

        return new InterchangeTransferDto(startingSection.getStartTime(),
                endingSection.getEndTime(),
                StationAssembler.assemble(startingSection.getStartStation()),
                StationAssembler.assemble(endingSection.getEndStation()),
                startingSection.getOperator());
    }


    public List<InterchangeTransferDto> getInterchanges(){return this.interchanges;}
}
