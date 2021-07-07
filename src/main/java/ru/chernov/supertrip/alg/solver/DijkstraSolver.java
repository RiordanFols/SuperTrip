package ru.chernov.supertrip.alg.solver;

import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
public class DijkstraSolver extends AStarSolver {

    public DijkstraSolver(Schedule schedule, Node start, Node end,
                          LocalDateTime startTime, LocalDateTime endTime,
                          Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);
    }

    @Override
    public long getTimeHeuristicFunction(Node node1, Node node2) {
        return 0;
    }
}
