package ru.chernov.supertrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.supertrip.domain.entity.Solution;

/**
 * @author Pavel Chernov
 */
public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
