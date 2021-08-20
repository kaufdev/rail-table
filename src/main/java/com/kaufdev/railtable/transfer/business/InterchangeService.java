package com.kaufdev.railtable.transfer.business;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.SectionEdgeAssembler;
import com.kaufdev.railtable.transfer.domain.SectionRepository;
import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import com.kaufdev.railtable.transfer.infrastracture.graph.SectionEdge;
import com.kaufdev.railtable.transfer.infrastracture.graph.ShorterPathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InterchangeService {
    private final ShorterPathFinder shorterPathFinder;
    private final SectionRepository sectionRepository;
    private final SectionEdgeAssembler sectionEdgeAssembler;
    private final StationAssembler stationAssembler;

    @Autowired
    public InterchangeService(ShorterPathFinder shorterPathFinder, SectionRepository sectionRepository, SectionEdgeAssembler sectionEdgeAssembler, StationAssembler stationAssembler) {
        this.shorterPathFinder = shorterPathFinder;
        this.sectionRepository = sectionRepository;
        this.sectionEdgeAssembler = sectionEdgeAssembler;
        this.stationAssembler = stationAssembler;
    }

    public List<TransferDto> findTransfers(String stationFrom, String stationTo, LocalDateTime outboundDate) {
        Map<Long, Section> sectionInTimeRangeMap = sectionRepository.findSectionsInTimeRange(outboundDate, outboundDate.plusDays(1L))
                .stream().collect(Collectors.toMap(Section::getId, Function.identity()));
        MutableNetwork<String, SectionEdge> network = NetworkBuilder.undirected().build();

        sectionInTimeRangeMap.values().forEach(section ->{
            network.addEdge(section.getStartStation().getAcronym(), section.getEndStation().getAcronym(), this.sectionEdgeAssembler.assemble(section));
        });

        List<Section> sectionPath = shorterPathFinder.getPath(network, stationFrom, stationTo).stream()
                .map(sectionInTimeRangeMap::get).collect(Collectors.toList());

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
