package ru.chernov.supertrip.alg.solver;

import lombok.Data;
import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.domain.entity.Solution;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public final Set<TransportType> transportTypesAvailable;
    public final SolutionType solutionType;
    public final List<Node> undone;
    public final Map<Node, Solution> solutions = new TreeMap<>(Comparator.comparing(Node::getName));

    public Solver(Schedule schedule, Node start, Node end,
                  LocalDateTime startTime, LocalDateTime endTime,
                  Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        this.schedule = schedule;
        this.start = start;
        this.end = end;
        this.startTime = startTime;
        this.endTime = endTime;
        this.transportTypesAvailable = transportTypesAvailable;
        this.solutionType = solutionType;

        // filtering trips by time and transport type
        schedule = filterSchedule(this.schedule);

        undone = new ArrayList<>(schedule.getNodes()) {{
            remove(start);
        }};
    }

    abstract public Solution solve();

    // filtering trips by time and transport type
    public Schedule filterSchedule(Schedule schedule) {
        schedule.setTrips(schedule.getTrips().stream()
                .filter(e -> transportTypesAvailable.contains(e.getType()))
                .filter(e -> e.getFromTime().isAfter(startTime))
                .filter(e -> e.getToTime().isBefore(endTime))
                .collect(Collectors.toSet()));
        return schedule;
    }
}
