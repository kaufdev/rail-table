package com.kaufdev.railtable.transfer.infrastracture.graph;

import java.time.LocalDateTime;

class SectionEdge {
    private final Long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    SectionEdge(Long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    LocalDateTime getEndTime(){
        return this.endTime;
    }

    Long getId() {
        return id;
    }
}
