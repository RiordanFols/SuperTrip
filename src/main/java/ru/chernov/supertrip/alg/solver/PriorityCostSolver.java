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


    private final long upTimeThreshold;
    private final long downTimeThreshold;
    private final double maxCost;

    public PriorityCostSolver(Schedule schedule, Node start, Node end, LocalDateTime startTime,
                              LocalDateTime endTime, Set<TransportType> transportTypesAvailable,
                              SolutionType solutionType, long minTime, long maxTime, double maxCost) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);
        upTimeThreshold = Math.round(minTime + (maxTime - minTime) * solutionType.getThreshold());
        downTimeThreshold = minTime;
        this.maxCost = maxCost;
    }

    @Override
    public boolean chooseSolution(Solution curSolution, double newWeight, double oldWeight,
                                  Node curNode, double newCost, double newTime) {
        if (curNode != end)
            return (curSolution == null || newWeight < oldWeight);
        else
            return (curSolution == null || newWeight < oldWeight)
                    && newTime < upTimeThreshold
                    && newTime > downTimeThreshold
                    && newCost <= maxCost;
    }
}
