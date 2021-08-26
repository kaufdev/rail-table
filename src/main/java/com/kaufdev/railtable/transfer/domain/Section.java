package com.kaufdev.railtable.transfer.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "START_STATION")
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "END_STATION")
    private Station endStation;

    @OneToOne
    @JoinColumn(name = "NEXT_SECTION")
    private Section nextSection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRANFER_ID")
    private Transfer transfer;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int length;

    public Section() { //JPA related
    }

    public Section(Station startStation, Station endStation, LocalDateTime startTime, LocalDateTime endTime, int length) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
    }

    public Long getTransferId(){
        return this.transfer.getId();
    }

    public String getOperator(){return this.transfer.getOperator();}

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

    public void setTransfer(Transfer transfer1) {
        this.transfer = transfer1;
    }

    public boolean hasStartStationAcronym(String startStationAcronym) {
        return startStation.hasAcronym(startStationAcronym);
    }

    public boolean hasEndStationAcronym(String endStationAcronym) {
        return endStation.hasAcronym(endStationAcronym);
    }

    public Transfer getTransfer(){
        return this.transfer;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", startStation=" + startStation +
                ", endStation=" + endStation +
                ", nextSection=" + nextSection +
                ", transfer=" + transfer +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", length=" + length +
                '}';
    }

    public Long getId() {
        return this.id;
    }
}
