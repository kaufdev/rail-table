package com.kaufdev.railtable.order;

import java.time.LocalDateTime;

public class TicketTransferDto implements Comparable<TicketTransferDto>{
    private final String outboundStationName;
    private final String arrivalStationName;
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;
    private final String operator;

    public TicketTransferDto(String outboundStationName,
                             String arrivalStationName,
                             LocalDateTime outboundTime,
                             LocalDateTime arrivalTime, String operator) {
        this.outboundStationName = outboundStationName;
        this.arrivalStationName = arrivalStationName;
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
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

    @Override
    public int compareTo(TicketTransferDto o) {
        return this.outboundTime.compareTo(o.getOutboundTime());
    }
}
