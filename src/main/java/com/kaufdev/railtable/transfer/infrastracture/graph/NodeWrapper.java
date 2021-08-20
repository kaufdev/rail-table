package com.kaufdev.railtable.transfer.infrastracture.graph;

import java.time.LocalDateTime;

public class NodeWrapper implements Comparable<NodeWrapper>{
    private String node;
    private NodeWrapper previous;
    private LocalDateTime timeOfArrival;
    private Long lastSectionId;

    public NodeWrapper(String node, NodeWrapper previous, LocalDateTime totalTimeInMinutes,Long lastSectionId) {
        this.node = node;
        this.previous = previous;
        this.timeOfArrival = totalTimeInMinutes;
        this.lastSectionId = lastSectionId;
    }

    public String getNode() {
        return node;
    }

    public NodeWrapper getPrevious() {
        return previous;
    }

    public LocalDateTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setPrevious(NodeWrapper previous){
        this.previous = previous;
    }

    @Override
    public int compareTo(NodeWrapper o) {
        return this.timeOfArrival.compareTo(o.getTimeOfArrival());
    }

    public void setTimeOfArrival(LocalDateTime newTimeOfArrival) {
        this.timeOfArrival = newTimeOfArrival;
    }

    public void update(NodeWrapper nodeWrapper, SectionEdge sectionEdge) {
        this.timeOfArrival = sectionEdge.getEndTime();
        this.lastSectionId = sectionEdge.getId();
        this.previous = nodeWrapper;
    }

    public Long getSectionId() {
        return this.lastSectionId;
    }
}