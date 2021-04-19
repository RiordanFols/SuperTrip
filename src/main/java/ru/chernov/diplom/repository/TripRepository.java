package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.diplom.domain.entity.Trip;

import java.util.Set;

/**
 * @author Pavel Chernov
 */
public interface TripRepository extends JpaRepository<Trip, Long> {
    Set<Trip> findAllByEdgeId(long edgeId);
}
