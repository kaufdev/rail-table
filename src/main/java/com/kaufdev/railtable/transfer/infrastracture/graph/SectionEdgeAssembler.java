package com.kaufdev.railtable.transfer.infrastracture.graph;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.infrastracture.graph.SectionEdge;
import org.springframework.stereotype.Component;

public class SectionEdgeAssembler {
    public static SectionEdge assemble(Section section){
        return new SectionEdge(section.getId(),section.getStartTime(),section.getEndTime());
    }
}
