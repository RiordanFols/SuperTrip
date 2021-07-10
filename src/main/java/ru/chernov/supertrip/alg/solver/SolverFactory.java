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
    public static Solver getProperSolver(Schedule schedule, Node start, Node end,
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

    public static Solver getProperSolver(Schedule schedule, Node start, Node end, LocalDateTime startTime,
                                         LocalDateTime endTime, Set<TransportType> transportTypesAvailable,
                                         SolutionType solutionType, long minTime, long maxTime,
                                         double minCost, double maxCost) {
        switch (solutionType) {
            case TIME_OPTIMAL -> {
                return new PriorityTimeSolver(schedule, start, end, startTime, endTime, transportTypesAvailable,
                        solutionType, minCost, maxCost, maxTime);
            }
            case COST_OPTIMAL -> {
                return new PriorityCostSolver(schedule, start, end, startTime, endTime, transportTypesAvailable,
                        solutionType, minTime, maxTime, maxCost);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
