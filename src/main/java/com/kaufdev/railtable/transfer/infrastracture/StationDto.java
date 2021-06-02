package com.kaufdev.railtable.transfer.infrastracture;

import java.util.Objects;

public class StationDto {
    private final String name;
    private final String city;
    private final String acronym;

    public StationDto(String name, String city, String acronym) {
        this.name = name;
        this.city = city;
        this.acronym = acronym;
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

    @Override
    public String toString() {
        return "StationDto{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", acronym='" + acronym + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationDto that = (StationDto) o;
        return Objects.equals(name, that.name) && Objects.equals(city, that.city) && Objects.equals(acronym, that.acronym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, acronym);
    }
}
