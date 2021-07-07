package ru.chernov.supertrip.alg.solver;

import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
public class SolverFactory {
    public static Solver getAppropriateSolver(Schedule schedule, Node start, Node end,
                                              LocalDateTime startTime, LocalDateTime endTime,
                                              Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        switch (solutionType) {
            case COST -> {
                return new DijkstraSolver(schedule, start, end,
                        startTime, endTime, transportTypesAvailable, solutionType);
            }
            case TIME -> {
                return new AStarSolver(schedule, start, end,
                        startTime, endTime, transportTypesAvailable, solutionType);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
