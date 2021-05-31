package com.kaufdev.railtable.transfer.infrastracture;

import com.kaufdev.railtable.transfer.domain.Station;
import org.springframework.stereotype.Component;

@Component
public class StationAssembler {
    public StationDto assembler(Station station){
        return new StationDto(station.getName(),station.getCity(),station.getAcronym());
    }
}
