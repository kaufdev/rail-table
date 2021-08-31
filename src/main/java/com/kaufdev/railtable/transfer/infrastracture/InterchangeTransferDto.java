package com.kaufdev.railtable.transfer.infrastracture;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final int allSeatsForSecondClass;
    private final int availableSeatsForSecondClass;
    private final int allSeatsForFirstClass;
    private final int availableSeatsForFirstClass;

    public InterchangeTransferDto(
            LocalDateTime outboundTime,
            LocalDateTime arrivalTime,
            String startStation,
            String endStation,
            String operator,
            BigDecimal firstClassCost,
            BigDecimal secondClassCost,
            Set<Long> sectionsIds,
            int allSeatsForSecondClass,
            int availableSeatsForSecondClass,
            int allSeatsForFirstClass,
            int availableSeatsForFirstClass) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.operator = operator;
        this.firstClassCost = firstClassCost;
        this.secondClassCost = secondClassCost;
        this.sectionsIds = sectionsIds;
        this.allSeatsForSecondClass = allSeatsForSecondClass;
        this.availableSeatsForSecondClass = availableSeatsForSecondClass;
        this.allSeatsForFirstClass = allSeatsForFirstClass;
        this.availableSeatsForFirstClass = availableSeatsForFirstClass;
    }

    public int getAllSeatsForSecondClass() {
        return allSeatsForSecondClass;
    }

    public int getAvailableSeatsForSecondClass() {
        return availableSeatsForSecondClass;
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

    public int getAllSeatsForFirstClass() {
        return allSeatsForFirstClass;
    }

    public int getAvailableSeatsForFirstClass() {
        return availableSeatsForFirstClass;
    }
}
