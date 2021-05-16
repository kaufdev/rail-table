package com.kaufdev.railtable.transfer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final Log logger = LogFactory.getLog("TransferController");

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/search")
    public List<TransferDto> findTransfers(@RequestBody SearchTransferDto searchTransferDto){
        return transferService.findTransfers(searchTransferDto);
    }
}
