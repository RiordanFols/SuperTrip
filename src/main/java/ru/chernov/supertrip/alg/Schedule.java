package ru.chernov.supertrip.alg;

import lombok.Data;
import ru.chernov.supertrip.domain.entity.Edge;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.domain.entity.Trip;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Data
public class Schedule {

    private Set<Trip> trips;
    private Set<Edge> edges = new HashSet<>();
    private Set<Node> nodes = new TreeSet<>(Comparator.comparing(Node::getName));

    public Schedule(Set<Trip> trips) {
        this.trips = trips;
        trips.forEach(e -> {
            edges.add(e.getEdge());
            nodes.add(e.getEdge().getFrom());
            nodes.add(e.getEdge().getTo());
        });
    }

    public boolean isEdgePresent(Node startNode, Node endNode) {
        return edges.stream()
                .anyMatch(e -> e.getFrom().equals(startNode) && e.getTo().equals(endNode));
    }

    public Set<Trip> findTripsByEdge(Edge edge) {
        return trips.stream()
                .filter(e -> e.getEdge().getFrom().equals(edge.getFrom()))
                .filter(e -> e.getEdge().getTo().equals(edge.getTo()))
                .collect(Collectors.toSet());
    }

    public Set<Trip> findTripsByNodes(Node start, Node end) {
        return trips.stream()
                .filter(e -> e.getEdge().getFrom().equals(start))
                .filter(e -> e.getEdge().getTo().equals(end))
                .collect(Collectors.toSet());
    }
}
