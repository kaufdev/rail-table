package com.kaufdev.railtable.transfer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String city;
    private String acronym;

    public Station() {//for JPA
    }

    public Station(String name, String city, String acronym) {
        this.name = name;
        this.city = city;
        this.acronym = acronym;
    }

    public boolean hasAcronym(String supposedName) {
        return acronym.equals(supposedName);
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAcronym() {
        return acronym;
    }
}
