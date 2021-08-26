package com.kaufdev.railtable.transfer.infrastracture.graph;

import com.google.common.graph.Network;
import com.kaufdev.railtable.transfer.domain.Section;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ShorterPathFinder {
    List<Section> getPath(Set<Section> possibleSectionsForDay, String startStation, String endStation);
}
