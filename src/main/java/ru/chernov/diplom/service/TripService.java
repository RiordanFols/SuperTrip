package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.entity.Trip;
import ru.chernov.diplom.repository.TripRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Set<Trip> getAll() {
        return new HashSet<>(tripRepository.findAll());
    }

    public TreeSet<Trip> getTripsByEdge(long edgeId) {
        return tripRepository.findAllByEdgeId(edgeId).stream()
                .sorted(Comparator.comparing(Trip::getFromTime))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public TreeSet<Trip> getTripsByEdge(long edgeId, int days) {
        var now = LocalDate.now();
        return tripRepository.findAllByEdgeId(edgeId).stream()
                // filtering for days from now
                .filter(e -> e.getFromTime().toLocalDate().isBefore(now.plusDays(days)))
                .sorted(Comparator.comparing(Trip::getFromTime))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
