package com.kaufdev.railtable.transfer;

public class Station {
    private String name;
    private String city;
    private String acronym;

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
