package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.entity.Edge;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.repository.EdgeRepository;

/**
 * @author Pavel Chernov
 */
@Service
public class EdgeService {

    private final EdgeRepository edgeRepository;

    @Autowired
    public EdgeService(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    public Edge save(Edge edge) {
        return edgeRepository.save(edge);
    }

    public Edge save(Node from, Node to) {
        Edge edge = new Edge();
        edge.setFrom(from);
        edge.setTo(to);
        return save(edge);
    }

    public boolean isPresent(Edge edge) {
        return findByNodes(edge.getFrom(), edge.getTo()) != null;
    }

    public Edge findByNodes(Node from, Node to) {
        Edge edge = edgeRepository.findByFromIdAndToId(from.getId(), to.getId());
        if (edge == null) {
            edge = new Edge();
            edge.setFrom(from);
            edge.setTo(to);
        }
        return edge;
    }
}
