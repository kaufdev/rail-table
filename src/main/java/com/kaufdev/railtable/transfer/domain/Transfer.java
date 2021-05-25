package com.kaufdev.railtable.transfer.domain;

import java.math.BigDecimal;
import java.util.Set;

public class Transfer {

    private String operator;
    private BigDecimal lengthCostFactor;

    //TODO MACIEK - somehow they need to be unique. Station can be only once startStation, and only once endStation
    private Set<Section> sections;

    public Transfer(String operator, BigDecimal lengthCostFactor, Set<Section> sections) {
        this.operator = operator;
        this.lengthCostFactor = lengthCostFactor;
        this.sections = sections;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public BigDecimal getLengthCostFactor() {
        return lengthCostFactor;
    }

    public String getOperator() {
        return operator;
    }
}
