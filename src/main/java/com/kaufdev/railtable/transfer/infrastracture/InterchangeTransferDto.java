package com.kaufdev.railtable.transfer.infrastracture;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class InterchangeTransferDto {
    private final String startStation;
    private final String endStation;
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;
    private final String operator;
    private final BigDecimal firstClassCost;
    private final BigDecimal secondClassCost;
    private final Set<Long> sectionsIds;
    private final int allSeats;
    private final int takenSeats;

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
                ", allSeats=" + allSeats +
                ", takenSeats=" + takenSeats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterchangeTransferDto that = (InterchangeTransferDto) o;
        return allSeats == that.allSeats && takenSeats == that.takenSeats && Objects.equals(startStation, that.startStation) && Objects.equals(endStation, that.endStation) && Objects.equals(outboundTime, that.outboundTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(operator, that.operator) && Objects.equals(firstClassCost, that.firstClassCost) && Objects.equals(secondClassCost, that.secondClassCost) && Objects.equals(sectionsIds, that.sectionsIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startStation, endStation, outboundTime, arrivalTime, operator, firstClassCost, secondClassCost, sectionsIds, allSeats, takenSeats);
    }

    public int getAllSeats() {
        return allSeats;
    }

    public int getTakenSeats() {
        return takenSeats;
    }

    public InterchangeTransferDto(
            LocalDateTime outboundTime,
            LocalDateTime arrivalTime,
            String startStation,
            String endStation,
            String operator,
            BigDecimal firstClassCost,
            BigDecimal secondClassCost, Set<Long> sectionsIds, int allSeats, int takenSeats) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.operator = operator;
        this.firstClassCost = firstClassCost;
        this.secondClassCost = secondClassCost;
        this.sectionsIds = sectionsIds;
        this.allSeats = allSeats;
        this.takenSeats = takenSeats;
    }

    public Set<Long> getSectionsIds() {
        return sectionsIds;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
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

}
