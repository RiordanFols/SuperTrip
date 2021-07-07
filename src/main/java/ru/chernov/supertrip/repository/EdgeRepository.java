package ru.chernov.supertrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.supertrip.domain.entity.Edge;

/**
 * @author Pavel Chernov
 */
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    Edge findByFromIdAndToId(long fromId, long toId);
}
