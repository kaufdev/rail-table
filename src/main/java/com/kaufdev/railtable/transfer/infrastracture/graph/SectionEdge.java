package com.kaufdev.railtable.transfer.infrastracture.graph;

import java.time.LocalDateTime;

public class SectionEdge {
    private final Long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;


    public SectionEdge(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime(){
        return this.endTime;
    }

    public long getId() {
        return id;
    }
}
