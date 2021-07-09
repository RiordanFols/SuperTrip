package ru.chernov.supertrip.alg.solver;

import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.domain.entity.Solution;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
public class PriorityTimeSolver extends AStarSolver {

    private final double upCostThreshold;
    private final double downCostThreshold;

    public PriorityTimeSolver(Schedule schedule, Node start, Node end, LocalDateTime startTime,
                              LocalDateTime endTime, Set<TransportType> transportTypesAvailable,
                              SolutionType solutionType, double minCost, double maxCost) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);
        upCostThreshold = minCost + (maxCost - minCost) * solutionType.getThreshold();
        downCostThreshold = minCost;
    }

    @Override
    public boolean chooseSolution(Solution curSolution, double newWeight, double oldWeight,
                                  Node curNode, double newCost, double newTime) {
        if (curNode != end)
            return (curSolution == null || newWeight < oldWeight);
        else
            return (curSolution == null || newWeight < oldWeight)
                    && newCost <= upCostThreshold
                    && newCost > downCostThreshold;
    }
}
