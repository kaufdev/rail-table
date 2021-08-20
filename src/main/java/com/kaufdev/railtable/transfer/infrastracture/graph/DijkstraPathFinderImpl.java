package com.kaufdev.railtable.transfer.infrastracture.graph;

import com.google.common.graph.Network;
import com.kaufdev.railtable.transfer.infrastracture.TransferNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DijkstraPathFinderImpl implements ShorterPathFinder {
    @Override
    public Optional<List<Long>> getPath(Network<String, SectionEdge> sectionNetwork, String startStation, String endStation) {
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

            for (String neighbourStation : sectionNetwork.adjacentNodes(node)) {
                if (nodesWithCalculatedTimeOfArrival.contains(neighbourStation)) {
                    continue;
                }

                SectionEdge sectionEdge = sectionNetwork.edgeConnecting(node, neighbourStation).orElseThrow(IllegalStateException::new);
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
        return Optional.empty();
    }

    private static Optional<List<Long>> buildPath(NodeWrapper nodeWrapper) {
        List<Long> path = new ArrayList<>();
        while (nodeWrapper != null && nodeWrapper.getPrevious() != null) {
            path.add(nodeWrapper.getSectionId());
            nodeWrapper = nodeWrapper.getPrevious();
        }
        Collections.reverse(path);
        return Optional.of(path);
    }
}
