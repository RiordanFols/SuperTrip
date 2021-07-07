package ru.chernov.supertrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Edge;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.domain.entity.Trip;
import ru.chernov.supertrip.repository.TripRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
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

    public Set<Trip> findAll() {
        return tripRepository.findByOrderByFromTimeAsc();
    }

    public Trip findById(long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public Trip save(Trip trip) {
        // create edge if doesn't exist yet
        if (edgeService.findById(trip.getEdge().getId()) == null)
            edgeService.save(trip.getEdge());
        return tripRepository.save(trip);
    }

    public Trip save(String fromCity, String toCity,
                     LocalDateTime fromTime, LocalDateTime toTime,
                     int cost, TransportType transportType) {
        Node from = nodeService.findByName(fromCity);
        Node to = nodeService.findByName(toCity);
        Edge edge = edgeService.findByNodes(from, to);

        Trip trip = new Trip(edge, fromTime, toTime, cost, transportType);
        return save(trip);
    }

    public TreeSet<Trip> getTripsByEdge(long edgeId) {
        return tripRepository.findAllByEdgeId(edgeId).stream()
                .sorted(Comparator.comparing(Trip::getFromTime))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public void deleteById(long id) {
        Trip trip = findById(id);
        if (trip != null) {
            tripRepository.deleteById(id);
            // deleting also the edge if no trips left
            var edgeId = trip.getEdge().getId();
            var trips = getTripsByEdge(edgeId);
            if (trips.isEmpty())
                edgeService.deleteById(edgeId);
        }
    }
}
