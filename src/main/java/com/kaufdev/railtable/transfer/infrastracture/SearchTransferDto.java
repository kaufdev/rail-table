package com.kaufdev.railtable.transfer.infrastracture;

import java.time.LocalDateTime;

public class SearchTransferDto {
    private final String stationFrom;
    private final String stationTo;
    private final LocalDateTime outboundDate;

    public SearchTransferDto(String stationFrom, String stationTo, LocalDateTime outboundDate) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.outboundDate = outboundDate;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public LocalDateTime getOutboundDate() {
        return outboundDate;
    }

    @Override
    public String toString() {
        return "SearchTransferDto{" +
                "stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", outboundDate=" + outboundDate +
                '}';
    }
}
