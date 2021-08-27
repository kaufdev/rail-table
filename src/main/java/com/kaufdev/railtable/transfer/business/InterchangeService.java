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

@Service
public class InterchangeService {
    private final SectionRepository sectionRepository;

    @Autowired
    public InterchangeService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<TransferDto> findTransfers(String stationFrom, String stationTo, LocalDateTime outboundDate) {
        Set<Section> allPossibleSections = sectionRepository.findSectionsInTimeRange(outboundDate, outboundDate.plusDays(1L));
        ShorterPathFinder pathFinder = new DijkstraPathFinderImpl(allPossibleSections);
        List<Section> sectionPath = pathFinder.getPath(stationFrom, stationTo);
        TransferByInterchanges transferByInterchanges = new TransferByInterchanges(sectionPath);

        if(sectionPath.size() < 2){//includes empty path and path with only one interchange, which is defacto transfer not interchange
            return Collections.emptyList();
        }

        Section firstSection = sectionPath.get(0);
        Section lastSection = sectionPath.get(sectionPath.size() - 1);

        TransferDto fastestTransferFromInterchanges = new TransferDto(firstSection.getStartTime(),
                lastSection.getEndTime(),
                StationAssembler.assemble(firstSection.getStartStation()),
                StationAssembler.assemble(lastSection.getEndStation()),
                transferByInterchanges.getOperators(),
                transferByInterchanges.getTotalFirstClassCost(),
                transferByInterchanges.getTotalSecondClassCost(),
                transferByInterchanges.getInterchanges(),
                transferByInterchanges.getAllSectionIds());

        return List.of(fastestTransferFromInterchanges);
    }
}
