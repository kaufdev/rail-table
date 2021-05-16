package com.kaufdev.railtable.transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransferDto {
    private final LocalDateTime outboundTime;
    private final LocalDateTime arrivalTime;
    private final String fromStationAcronym;
    private final String endStationAcronym;
    private final String fromStationName;
    private final String endStationName;
    private final String operator;
    private final BigDecimal firstClassPrice;
    private final BigDecimal secondClassPrice;

    public TransferDto(LocalDateTime outboundTime,
                       LocalDateTime arrivalTime,
                       String fromStationAcronym,
                       String endStationAcronym,
                       String fromStationName,
                       String endStationName,
                       String operator,
                       BigDecimal firstClassPrice,
                       BigDecimal secondClassPrice) {
        this.outboundTime = outboundTime;
        this.arrivalTime = arrivalTime;
        this.fromStationAcronym = fromStationAcronym;
        this.endStationAcronym = endStationAcronym;
        this.fromStationName = fromStationName;
        this.endStationName = endStationName;
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

    public String getFromStationAcronym() {
        return fromStationAcronym;
    }

    public String getEndStationAcronym() {
        return endStationAcronym;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferDto that = (TransferDto) o;
        return firstClassPrice.equals(that.firstClassPrice) && secondClassPrice.equals(that.secondClassPrice) && Objects.equals(outboundTime, that.outboundTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(fromStationAcronym, that.fromStationAcronym) && Objects.equals(endStationAcronym, that.endStationAcronym) && Objects.equals(operator, that.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outboundTime, arrivalTime, fromStationAcronym, endStationAcronym, operator, firstClassPrice, secondClassPrice);
    }

    @Override
    public String toString() {
        return "TransferDto{" +
                "outboundTime=" + outboundTime +
                ", arrivalTime=" + arrivalTime +
                ", fromStationAcronym='" + fromStationAcronym + '\'' +
                ", endStationAcronym='" + endStationAcronym + '\'' +
                ", fromStationName='" + fromStationName + '\'' +
                ", endStationName='" + endStationName + '\'' +
                ", operator='" + operator + '\'' +
                ", firstClassPrice=" + firstClassPrice +
                ", secondClassPrice=" + secondClassPrice +
                '}';
    }
}
