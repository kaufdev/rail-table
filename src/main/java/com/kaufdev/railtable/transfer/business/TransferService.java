package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.TransferRepository;
import com.kaufdev.railtable.transfer.infrastracture.SearchTransferDto;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferAssembler transferAssembler;
    private final InterchangeService interchangeService;

    @Autowired
    public TransferService(TransferRepository transferRepository, TransferAssembler transferAssembler, InterchangeService interchangeService) {
        this.transferRepository = transferRepository;
        this.transferAssembler = transferAssembler;
        this.interchangeService = interchangeService;
    }

    public List<TransferDto> findTransfers(SearchTransferDto searchTransferDto){
        List<TransferDto> transfersWithoutInterchange = transferRepository.findTransfers(searchTransferDto.getStationFrom(), searchTransferDto.getStationTo(), searchTransferDto.getOutboundDate()).stream()
                .map(transfer -> transferAssembler.assemble(transfer, searchTransferDto.getStationFrom(), searchTransferDto.getStationTo()))
                .sorted(Comparator.comparing(TransferDto::getArrivalTime))
                .collect(Collectors.toList());

        List<TransferDto> transfersBuiltByInterchanges = interchangeService.findTransfers(searchTransferDto.getStationFrom(), searchTransferDto.getStationTo(), searchTransferDto.getOutboundDate());

        return Stream.of(transfersWithoutInterchange,transfersBuiltByInterchanges)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(TransferDto::getArrivalTime))
                .collect(Collectors.toList());
    }
}
