package ru.chernov.supertrip.service;

import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.repository.NodeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    public List<Node> findAllSorted() {
        return nodeRepository.findByOrderByName();
    }
}
