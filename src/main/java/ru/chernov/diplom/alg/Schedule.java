package ru.chernov.diplom.alg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.chernov.diplom.domain.entity.Node;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Data
public class Schedule {

    private Set<Edge> edges;
    private Set<Node> nodes = new TreeSet<>(Comparator.comparing(Node::getName));

    public Schedule(Set<Edge> edges) {
        this.edges = edges;

        Set<Node> setFrom = edges.stream()
                .map(Edge::getFrom)
                .collect(Collectors.toSet());

        Set<Node> setTo = edges.stream()
                .map(Edge::getTo)
                .collect(Collectors.toSet());

        this.nodes.addAll(setFrom);
        this.nodes.addAll(setTo);
    }

    public Edge findEdgeByNodes(Node from, Node to) {
        return edges.stream()
                .filter(e -> e.getFrom().equals(from))
                .filter(e -> e.getTo().equals(to))
                .findFirst().orElse(null);

    }
}
