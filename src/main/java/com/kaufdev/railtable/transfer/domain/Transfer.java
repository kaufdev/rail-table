package com.kaufdev.railtable.transfer.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "transfer", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Section> sections;

    private String operator;
    private BigDecimal lengthCostFactor;

    public Transfer() {//JPA related
    }

    public Transfer(String operator, BigDecimal lengthCostFactor, Set<Section> sections) {
        this.operator = operator;
        this.lengthCostFactor = lengthCostFactor;
        this.sections = sections;
    }

    public Transfer(String operator, BigDecimal lengthCostFactor) {
        this.operator = operator;
        this.lengthCostFactor = lengthCostFactor;
    }

    public void setSections(Set<Section> sections) {
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

    public Long getId(){return id;}
}
