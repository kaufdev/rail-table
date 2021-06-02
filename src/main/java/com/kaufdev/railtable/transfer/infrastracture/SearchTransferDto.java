package com.kaufdev.railtable.transfer.infrastracture;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class SearchTransferDto {
    private String stationFrom;
    private String stationTo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outboundDate;

    public SearchTransferDto() {//Jackson needed
    }

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
