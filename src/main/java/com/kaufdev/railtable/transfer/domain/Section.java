package com.kaufdev.railtable.transfer.domain;

import java.time.LocalDateTime;

public class Section {
    private Station startStation;
    private Station endStation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int length;
    private Section nextSection;

    public Section(Station startStation, Station endStation, LocalDateTime startTime, LocalDateTime endTime, int length) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
    }

    public Section getNextSection(){
        return this.nextSection;
    }

    public Section setNextSection(Section section){
        this.nextSection = section;
        return this;
    }

    public Station getStartStation() {
        return startStation;
    }

    public String getStartStationName(){return startStation.getName();}

    public String getEndStationName(){return endStation.getName();}

    public Station getEndStation() {
        return endStation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getLength() {
        return length;
    }

    public boolean hasStartStationAcronym(String startStationAcronym) {
        return startStation.hasAcronym(startStationAcronym);
    }

    public boolean hasEndStationAcronym(String endStationAcronym) {
        return endStation.hasAcronym(endStationAcronym);
    }
}
