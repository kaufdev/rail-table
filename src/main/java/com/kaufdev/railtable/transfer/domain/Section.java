package com.kaufdev.railtable.transfer.domain;

import com.kaufdev.railtable.order.Ticket;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToMany(mappedBy = "boughtSections")
    private Set<Ticket> tickets = new HashSet<>();

    private int availableSeatsForSecondClass;
    private int allSeatsForSecondClass;
    private int availableSeatsForFirstClass;
    private int allSeatsForFirstClass;
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

    public int getAvailableSeatsForFirstClass() {
        return availableSeatsForFirstClass;
    }

    public int getAllSeatsForFirstClass() {
        return allSeatsForFirstClass;
    }

    public boolean isEmptySeatPossibleForSecondClass(){
        return availableSeatsForSecondClass > 0;
    }


    public boolean isEmptySeatPossibleForFirstClass() {
        return availableSeatsForFirstClass > 0;
    }

    public int getAvailableSeatsForSecondClass() {
        return availableSeatsForSecondClass;
    }

    public int getAllSeatsForSecondClass() {
        return allSeatsForSecondClass;
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

    public Long getId() {
        return this.id;
    }

    public void takeSeatForSecondClass() {
        availableSeatsForSecondClass--;
    }

    public void takeSeatForFirstClass() {
        availableSeatsForFirstClass--;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", startStation=" + startStation +
                ", endStation=" + endStation +
                ", nextSection=" + nextSection +
                ", transfer=" + transfer +
                ", tickets=" + tickets +
                ", availableSeatsForSecondClass=" + availableSeatsForSecondClass +
                ", allSeatsForSecondClass=" + allSeatsForSecondClass +
                ", availableSeatsForFirstClass=" + availableSeatsForFirstClass +
                ", allSeatsForFirstClass=" + allSeatsForFirstClass +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return availableSeatsForSecondClass == section.availableSeatsForSecondClass && allSeatsForSecondClass == section.allSeatsForSecondClass && availableSeatsForFirstClass == section.availableSeatsForFirstClass && allSeatsForFirstClass == section.allSeatsForFirstClass && length == section.length && Objects.equals(id, section.id) && Objects.equals(startStation, section.startStation) && Objects.equals(endStation, section.endStation) && Objects.equals(nextSection, section.nextSection) && Objects.equals(transfer, section.transfer) && Objects.equals(tickets, section.tickets) && Objects.equals(startTime, section.startTime) && Objects.equals(endTime, section.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startStation, endStation, nextSection, transfer, tickets, availableSeatsForSecondClass, allSeatsForSecondClass, availableSeatsForFirstClass, allSeatsForFirstClass, startTime, endTime, length);
    }

}
