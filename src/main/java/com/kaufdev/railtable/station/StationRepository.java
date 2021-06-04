package com.kaufdev.railtable.station;

import com.kaufdev.railtable.transfer.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository  extends JpaRepository<Station, Long> {
    List<Station> findByNameStartsWithIgnoreCase(String name);
}
