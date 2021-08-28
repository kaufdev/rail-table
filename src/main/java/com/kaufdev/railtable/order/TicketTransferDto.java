package com.kaufdev.railtable.order;

import java.time.LocalDateTime;

public class TicketTransferDto {
    private final String outboundStationName;
    private final String arrivalStationName;
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;

    public TicketTransferDto(String outboundStationName,
                             String arrivalStationName,
                             LocalDateTime outboundTime,
                             LocalDateTime arrivalTime) {
        this.outboundStationName = outboundStationName;
        this.arrivalStationName = arrivalStationName;
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
    }

    public String getOutboundStationName() {
        return outboundStationName;
    }

    public String getArrivalStationName() {
        return arrivalStationName;
    }

    public LocalDateTime getOutboundTime() {
        return outboundTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
