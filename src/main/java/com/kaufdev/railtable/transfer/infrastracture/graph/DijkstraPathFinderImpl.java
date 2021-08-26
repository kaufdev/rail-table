package com.kaufdev.railtable.transfer.infrastracture.graph;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.SectionEdgeAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DijkstraPathFinderImpl implements ShorterPathFinder {

    @Override
    public List<Section> getPath(Set<Section> allPossibleSections, String startStation, String endStation) {

        Map<Long, Section> sectionInTimeRangeMap = allPossibleSections.stream()
                .collect(Collectors.toMap(Section::getId, Function.identity()));

        MutableNetwork<String, SectionEdge> network = NetworkBuilder.undirected().build();

        sectionInTimeRangeMap.values().forEach(section ->{
            network.addEdge(section.getStartStation().getAcronym(), section.getEndStation().getAcronym(), SectionEdgeAssembler.assemble(section));
        });

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
                return buildPath(nodeWrapper, sectionInTimeRangeMap);
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

    private static List<Section> buildPath(NodeWrapper nodeWrapper, Map<Long, Section> sectionInTimeRangeMap) {
        List<Section> path = new ArrayList<>();
        while (nodeWrapper != null && nodeWrapper.getPrevious() != null) {
            path.add(sectionInTimeRangeMap.get(nodeWrapper.getSectionId()));
            nodeWrapper = nodeWrapper.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }
}
