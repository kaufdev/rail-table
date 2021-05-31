package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.TransferRepository;
import com.kaufdev.railtable.transfer.infrastracture.SearchTransferDto;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferAssembler transferAssembler;

    @Autowired
    public TransferService(TransferRepository transferRepository, TransferAssembler transferAssembler) {
        this.transferRepository = transferRepository;
        this.transferAssembler = transferAssembler;
    }

    public List<TransferDto> findTransfers(SearchTransferDto searchTransferDto){
        return transferRepository.findTransfers(searchTransferDto.getStationFrom(), searchTransferDto.getStationTo(), searchTransferDto.getOutboundDate()).stream()
                .map(transfer -> transferAssembler.assemble(transfer, searchTransferDto.getStationFrom(), searchTransferDto.getStationTo()))
                .sorted(Comparator.comparing(TransferDto::getArrivalTime))
                .collect(Collectors.toList());
    }
}
