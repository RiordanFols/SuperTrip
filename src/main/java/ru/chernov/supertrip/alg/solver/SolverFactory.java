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
                                         SolutionType solutionType, double minWeight, double maxWeight) {
        switch (solutionType) {
            case TIME_OPTIMAL -> {
                return new PriorityTimeSolver(schedule, start, end, startTime, endTime, transportTypesAvailable,
                        solutionType, minWeight, maxWeight);
            }
            case COST_OPTIMAL -> {
                return new PriorityCostSolver(schedule, start, end, startTime, endTime, transportTypesAvailable,
                        solutionType, minWeight, maxWeight);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
