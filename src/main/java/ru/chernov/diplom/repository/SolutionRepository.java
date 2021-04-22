package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.diplom.domain.entity.Solution;

/**
 * @author Pavel Chernov
 */
public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
