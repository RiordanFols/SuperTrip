package ru.chernov.supertrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.supertrip.domain.entity.Trip;

import java.util.Set;

/**
 * @author Pavel Chernov
 */
public interface TripRepository extends JpaRepository<Trip, Long> {
    Set<Trip> findByOrderByFromTimeAsc();

    Set<Trip> findAllByEdgeId(long edgeId);
}
