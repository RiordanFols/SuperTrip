package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.diplom.domain.entity.Edge;

/**
 * @author Pavel Chernov
 */
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    Edge findByFromIdAndToId(long fromId, long toId);
}
