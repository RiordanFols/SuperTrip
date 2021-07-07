package ru.chernov.supertrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.repository.NodeRepository;

/**
 * @author Pavel Chernov
 */
@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node findByName(String name) {
        return nodeRepository.findByName(name);
    }
}
