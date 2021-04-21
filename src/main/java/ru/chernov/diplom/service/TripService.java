package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Edge;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Trip;
import ru.chernov.diplom.repository.NodeRepository;
import ru.chernov.diplom.repository.TripRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final NodeService nodeService;
    private final EdgeService edgeService;

    @Autowired
    public TripService(TripRepository tripRepository, NodeService nodeService, EdgeService edgeService) {
        this.tripRepository = tripRepository;
        this.nodeService = nodeService;
        this.edgeService = edgeService;
    }

    public Set<Trip> getAll() {
        return new HashSet<>(tripRepository.findAll());
    }

    public Trip save(Trip trip) {
        // create edge if doesn't exist yet
        if (!edgeService.isPresent(trip.getEdge()))
            edgeService.save(trip.getEdge());
        return tripRepository.save(trip);
    }

    public Trip save(String fromCity, String toCity,
                     LocalDateTime fromTime, LocalDateTime toTime,
                     int cost, TransportType transportType) {
        Node from = nodeService.findByName(fromCity);
        Node to = nodeService.findByName(toCity);
        Edge edge = edgeService.findByNodes(from, to);

        Trip trip = new Trip();
        trip.setEdge(edge);
        trip.setFromTime(fromTime);
        trip.setToTime(toTime);
        trip.setType(transportType);
        return save(trip);
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
