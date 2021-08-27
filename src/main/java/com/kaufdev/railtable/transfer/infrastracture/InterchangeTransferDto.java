package com.kaufdev.railtable.transfer.infrastracture;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class InterchangeTransferDto {
    private final StationDto startStation;
    private final StationDto endStation;
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;
    private final String operator;
    private final BigDecimal firstClassCost;
    private final BigDecimal secondClassCost;
    private final Set<Long> sectionsIds;

    public InterchangeTransferDto(
            LocalDateTime outboundTime,
            LocalDateTime arrivalTime,
            StationDto startStation,
            StationDto endStation,
            String operator,
            BigDecimal firstClassCost,
            BigDecimal secondClassCost, Set<Long> sectionsIds) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.operator = operator;
        this.firstClassCost = firstClassCost;
        this.secondClassCost = secondClassCost;
        this.sectionsIds = sectionsIds;
    }

    public Set<Long> getSectionsIds() {
        return sectionsIds;
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

    public BigDecimal getFirstClassCost() {
        return firstClassCost;
    }

    public BigDecimal getSecondClassCost() {
        return secondClassCost;
    }

    @Override
    public String toString() {
        return "InterchangeTransferDto{" +
                "startStation=" + startStation +
                ", endStation=" + endStation +
                ", outboundTime=" + outboundTime +
                ", arrivalTime=" + arrivalTime +
                ", operator='" + operator + '\'' +
                ", firstClassCost=" + firstClassCost +
                ", secondClassCost=" + secondClassCost +
                ", sectionsIds=" + sectionsIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterchangeTransferDto that = (InterchangeTransferDto) o;
        return Objects.equals(startStation, that.startStation) && Objects.equals(endStation, that.endStation) && Objects.equals(outboundTime, that.outboundTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(operator, that.operator) && Objects.equals(firstClassCost, that.firstClassCost) && Objects.equals(secondClassCost, that.secondClassCost) && Objects.equals(sectionsIds, that.sectionsIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startStation, endStation, outboundTime, arrivalTime, operator, firstClassCost, secondClassCost, sectionsIds);
    }
}
