package com.kaufdev.railtable.transfer.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SectionRepository {
    @Query("SELECT s from Section s " +
            "WHERE s.startTime >= :outboundDate " +
            "AND s.endTime <= :limitArrivalTime ")
    List<Section> findSectionsInTimeRange(@Param("outboundTime") LocalDateTime outboundTime,@Param("limitArrivalTime") LocalDateTime limitArrivalTime);
}
