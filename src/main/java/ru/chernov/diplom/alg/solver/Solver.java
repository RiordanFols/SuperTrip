package ru.chernov.diplom.alg.solver;

import lombok.Data;
import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Data
public abstract class Solver {

    public Schedule schedule;
    public final Node start;
    public final Node end;
    public final LocalDateTime startTime;
    public final LocalDateTime endTime;
    public final int maxTransfersNumber;
    public final Set<TransportType> transportTypesAvailable;
    public final SolutionType solutionType;

    public Solver(Schedule schedule, Node start, Node end, LocalDateTime startTime, LocalDateTime endTime,
                  int maxTransfersNumber, Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        this.schedule = schedule;
        this.start = start;
        this.end = end;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxTransfersNumber = maxTransfersNumber;
        this.transportTypesAvailable = transportTypesAvailable;
        this.solutionType = solutionType;
    }

    abstract public void solve();

    // filtering trips by time and transport type
    public Schedule filterSchedule(Schedule schedule) {
        for (var edge : schedule.getEdges()) {
            edge.getTrips().removeIf(trip -> !transportTypesAvailable.contains(trip.getType()));
            edge.getTrips().removeIf(trip -> trip.getFromTime().isBefore(startTime));
            edge.getTrips().removeIf(trip -> trip.getToTime().isAfter(endTime));
        }
        return schedule;
    }
}
