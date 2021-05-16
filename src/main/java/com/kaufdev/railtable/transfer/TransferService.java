package com.kaufdev.railtable.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
//    private final TransferAssembler transferAssembler;

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    List<TransferDto> findTransfers(SearchTransferDto searchTransferDto){
        return List.of();
    }
}
