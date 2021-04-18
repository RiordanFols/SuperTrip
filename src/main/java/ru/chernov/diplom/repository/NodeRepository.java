package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.diplom.domain.entity.Node;

/**
 * @author Pavel Chernov
 */
public interface NodeRepository extends JpaRepository<Node, Long> {
}
