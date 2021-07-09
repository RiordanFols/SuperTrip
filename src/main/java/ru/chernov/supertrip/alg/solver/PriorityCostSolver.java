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
public class PriorityCostSolver extends DijkstraSolver {


    private final double upTimeThreshold;
    private final double downTimeThreshold;

    public PriorityCostSolver(Schedule schedule, Node start, Node end, LocalDateTime startTime,
                              LocalDateTime endTime, Set<TransportType> transportTypesAvailable,
                              SolutionType solutionType, double minTime, double maxTime) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);
        upTimeThreshold = minTime + (maxTime - minTime) * solutionType.getThreshold();
        downTimeThreshold = minTime;
    }

    @Override
    public boolean chooseSolution(Solution curSolution, double newWeight, double oldWeight,
                                  Node curNode, double newCost, double newTime) {
        if (curNode != end)
            return (curSolution == null || newWeight < oldWeight);
        else
            return (curSolution == null || newWeight < oldWeight)
                    && newTime <= upTimeThreshold
                    && newTime > downTimeThreshold;
    }
}
