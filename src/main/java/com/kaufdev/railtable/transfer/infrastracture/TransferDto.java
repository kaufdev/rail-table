package com.kaufdev.railtable.transfer.infrastracture;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransferDto {
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;
    private final StationDto startStation;
    private final StationDto endStation;
    private final String operator;
    private final BigDecimal firstClassPrice;
    private final BigDecimal secondClassPrice;

    public TransferDto(LocalDateTime outboundTime,
                       LocalDateTime arrivalTime,
                       StationDto startStation,
                       StationDto endStation,
                       String operator,
                       BigDecimal firstClassPrice,
                       BigDecimal secondClassPrice) {
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.startStation = startStation;
        this.endStation = endStation;
        this.operator = operator;
        this.firstClassPrice = firstClassPrice;
        this.secondClassPrice = secondClassPrice;
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

    public BigDecimal getFirstClassPrice() {
        return firstClassPrice;
    }

    public BigDecimal getSecondClassPrice() {
        return secondClassPrice;
    }

    public StationDto getStartStation() {
        return startStation;
    }

    public StationDto getEndStation() {
        return endStation;
    }

    @Override
    public String toString() {
        return "TransferDto{" +
                "outboundTime=" + outboundTime +
                ", arrivalTime=" + arrivalTime +
                ", startStation=" + startStation +
                ", endStation=" + endStation +
                ", operator='" + operator + '\'' +
                ", firstClassPrice=" + firstClassPrice +
                ", secondClassPrice=" + secondClassPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferDto that = (TransferDto) o;
        return Objects.equals(outboundTime, that.outboundTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(startStation, that.startStation) && Objects.equals(endStation, that.endStation) && Objects.equals(operator, that.operator) && Objects.equals(firstClassPrice, that.firstClassPrice) && Objects.equals(secondClassPrice, that.secondClassPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outboundTime, arrivalTime, startStation, endStation, operator, firstClassPrice, secondClassPrice);
    }

}
