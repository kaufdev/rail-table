package com.kaufdev.railtable.transfer.infrastracture;

import com.kaufdev.railtable.transfer.domain.Station;

import java.time.LocalDateTime;
import java.util.Objects;

public class InterchangeTransferDto {
    private final StationDto startStation;
    private final StationDto endStation;
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;
    private final String operator;

    public InterchangeTransferDto(
            LocalDateTime outboundTime,
            LocalDateTime arrivalTime,
            StationDto startStation,
            StationDto endStation,
            String operator) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.operator = operator;
    }

    public StationDto getStartStation() {
        return startStation;
    }

    public StationDto getEndStation() {
        return endStation;
    }

    public LocalDateTime getOutboundTime() {
        return outboundTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterchangeTransferDto that = (InterchangeTransferDto) o;
        return Objects.equals(startStation, that.startStation) && Objects.equals(endStation, that.endStation) && Objects.equals(outboundTime, that.outboundTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(operator, that.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startStation, endStation, outboundTime, arrivalTime, operator);
    }

    @Override
    public String toString() {
        return "InterchangeTransferDto{" +
                "startStation=" + startStation +
                ", endStation=" + endStation +
                ", outboundTime=" + outboundTime +
                ", arrivalTime=" + arrivalTime +
                ", operator='" + operator + '\'' +
                '}';
    }
}
