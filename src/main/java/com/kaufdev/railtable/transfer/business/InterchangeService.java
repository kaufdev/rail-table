package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.SectionRepository;
import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import com.kaufdev.railtable.transfer.infrastracture.graph.DijkstraPathFinderImpl;
import com.kaufdev.railtable.transfer.infrastracture.graph.ShorterPathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InterchangeService {
    private final SectionRepository sectionRepository;
    private final StationAssembler stationAssembler;

    @Autowired
    public InterchangeService(SectionRepository sectionRepository , StationAssembler stationAssembler) {
        this.sectionRepository = sectionRepository;
        this.stationAssembler = stationAssembler;
    }

    public List<TransferDto> findTransfers(String stationFrom, String stationTo, LocalDateTime outboundDate) {
        Set<Section> allPossibleSections = sectionRepository.findSectionsInTimeRange(outboundDate, outboundDate.plusDays(1L));
        ShorterPathFinder pathFinder = new DijkstraPathFinderImpl(allPossibleSections);
        List<Section> sectionPath = pathFinder.getPath(stationFrom, stationTo);

        if(sectionPath.isEmpty() || checkIfSectionsAreFromTheSameTransfer(sectionPath)){
            return Collections.emptyList();
        }

        Section firstSection = sectionPath.get(0);
        Section lastSection = sectionPath.get(sectionPath.size() - 1);

        TransferDto fastestTransferFromInterchanges = new TransferDto(firstSection.getStartTime(),
                lastSection.getEndTime(),
                stationAssembler.assemble(firstSection.getStartStation()),
                stationAssembler.assemble(lastSection.getEndStation()),
                null,
                null,
                null,
                Collections.emptyList()
        );

        return List.of(fastestTransferFromInterchanges);
    }

    private boolean checkIfSectionsAreFromTheSameTransfer(List<Section> sections){
        return sections.stream().map(Section::getTransferId).collect(Collectors.toSet()).size() == 1;
    }
}
