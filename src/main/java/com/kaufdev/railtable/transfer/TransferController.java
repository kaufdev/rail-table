package com.kaufdev.railtable.transfer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final Log logger = LogFactory.getLog("TransferController");

    @PostMapping("/search")
    public Transfer searchTransfer(@RequestBody SearchTransferDto searchTransferDto){
        logger.info(searchTransferDto);
        return null;
    }
}
