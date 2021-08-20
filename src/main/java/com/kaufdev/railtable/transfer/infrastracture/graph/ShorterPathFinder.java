package com.kaufdev.railtable.transfer.infrastracture.graph;

import com.google.common.graph.Network;

import java.util.List;
import java.util.Optional;

public interface ShorterPathFinder {
    List<Long> getPath(Network<String, SectionEdge> sectionNetwork, String startStation, String endStation);
}
