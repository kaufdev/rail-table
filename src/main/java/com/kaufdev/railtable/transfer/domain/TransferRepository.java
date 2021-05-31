package com.kaufdev.railtable.transfer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query("Select t from Transfer t " +
            " join t.sections as startSection " +
            " join t.sections as toSection " +
            " where startSection.startStation.acronym = :stationFrom " +
            " and startSection.startTime >= :outboundDate "+
            " and toSection.endStation.acronym = :stationTo ")
    List<Transfer> findTransfers(@Param("stationFrom") String stationFrom,
                                 @Param("stationTo") String stationTo,
                                 @Param("outboundDate") LocalDateTime outboundDate);
}
