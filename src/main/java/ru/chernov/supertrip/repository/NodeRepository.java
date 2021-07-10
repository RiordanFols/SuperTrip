package ru.chernov.supertrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.supertrip.domain.entity.Node;

import java.util.List;

/**
 * @author Pavel Chernov
 */
public interface NodeRepository extends JpaRepository<Node, Long> {
    Node findByName(String name);

    List<Node> findByOrderByName();
}
