package com.kaufdev.railtable.transfer.infrastracture.graph;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.kaufdev.railtable.transfer.domain.Section;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DijkstraPathFinderImpl implements ShorterPathFinder {

    private final Map<Long, Section> allPossibleSectionGroupedById;
    private final MutableNetwork<String, SectionEdge> network = NetworkBuilder.undirected().build();

    public DijkstraPathFinderImpl(Set<Section> allPossibleSections) {
        allPossibleSectionGroupedById = allPossibleSections.stream()
                .collect(Collectors.toMap(Section::getId, Function.identity()));

        allPossibleSectionGroupedById.values().forEach(section ->{
            network.addEdge(section.getStartStation().getAcronym(), section.getEndStation().getAcronym(), SectionEdgeAssembler.assemble(section));
        });
    }

    @Override
    public List<Section> getPath(String startStation, String endStation) {
        Map<String, NodeWrapper> nodeWrappers = new HashMap<>();
        PriorityQueue<NodeWrapper> queue = new PriorityQueue<>();
        Set<String> nodesWithCalculatedTimeOfArrival = new HashSet<>();

        NodeWrapper startPointWrapper = new NodeWrapper(startStation, null, LocalDateTime.MIN,null);
        nodeWrappers.put(startStation, startPointWrapper);
        queue.add(startPointWrapper);
        while (!queue.isEmpty()) {
            NodeWrapper nodeWrapper = queue.poll();
            String node = nodeWrapper.getNode();
            nodesWithCalculatedTimeOfArrival.add(node);

            if (node.equals(endStation)) {
                return buildPath(nodeWrapper);
            }

            for (String neighbourStation : network.adjacentNodes(node)) {
                if (nodesWithCalculatedTimeOfArrival.contains(neighbourStation)) {
                    continue;
                }

                SectionEdge sectionEdge = network.edgeConnecting(node, neighbourStation).orElseThrow(IllegalStateException::new);
                NodeWrapper neighborWrapper = nodeWrappers.get(neighbourStation);
                if (neighborWrapper == null) {
                    neighborWrapper = new NodeWrapper(neighbourStation, nodeWrapper, sectionEdge.getEndTime(), sectionEdge.getId());
                    nodeWrappers.put(neighbourStation, neighborWrapper);
                    queue.add(neighborWrapper);
                } else if (neighborWrapper.getTimeOfArrival().isAfter(sectionEdge.getEndTime())) {
                    neighborWrapper.update(nodeWrapper,sectionEdge);
                    queue.remove(neighborWrapper);
                    queue.add(neighborWrapper);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Section> buildPath(NodeWrapper nodeWrapper) {
        List<Section> path = new ArrayList<>();
        while (nodeWrapper != null && nodeWrapper.getPrevious() != null) {
            path.add(allPossibleSectionGroupedById.get(nodeWrapper.getSectionId()));
            nodeWrapper = nodeWrapper.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }
}
