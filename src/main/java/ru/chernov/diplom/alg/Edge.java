package ru.chernov.diplom.alg;

import lombok.Data;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Trip;

import java.util.*;

/**
 * @author Pavel Chernov
 */
@Data
public class Edge {
    private final Node from;
    private final Node to;
    private final Set<Trip> trips = new TreeSet<>(Comparator.comparingLong(Trip::getId));
//    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

//    public void addTrip(TransportType transport, String timeFrom, String timeTo, int price) {
//        this.getTrips().add(new Trip(from, to, LocalDateTime.parse(timeFrom, dtf),
//                LocalDateTime.parse(timeTo, dtf), price, transport));
//    }
}