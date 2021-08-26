package com.kaufdev.railtable.transfer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s from Section s " +
            "WHERE s.startTime >= :outboundTime " +
            "AND s.endTime <= :limitArrivalTime ")
    Set<Section> findSectionsInTimeRange(@Param("outboundTime") LocalDateTime outboundTime,
                                         @Param("limitArrivalTime") LocalDateTime limitArrivalTime);
}
