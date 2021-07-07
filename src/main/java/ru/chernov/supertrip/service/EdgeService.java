package ru.chernov.supertrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.supertrip.domain.entity.Edge;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.repository.EdgeRepository;

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

    public void save(Edge edge) {
        edgeRepository.save(edge);
    }

    public Edge findById(long id) {
        return edgeRepository.findById(id).orElse(null);
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

    public void deleteById(long id) {
        edgeRepository.deleteById(id);
    }
}
