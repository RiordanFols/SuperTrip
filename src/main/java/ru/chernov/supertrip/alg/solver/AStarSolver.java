package ru.chernov.supertrip.alg.solver;

import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
public class AStarSolver extends DijkstraSolver {

    public AStarSolver(Schedule schedule, Node start, Node end,
                       LocalDateTime startTime, LocalDateTime endTime,
                       Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);
    }

    @Override
    public boolean checkIfRouteTooBig(Node minNode) {
        var minNodeSolution = solutions.get(minNode);
        var endSolution = solutions.get(end);
        // if can't compare solutions
        if (minNodeSolution == null || endSolution == null)
            return false;

        return switch (solutionType) {
            case COST -> minNodeSolution.getCost() >= endSolution.getCost();
            case TIME ->  minNodeSolution.getTime() + getTimeHeuristicFunction(minNode, end) >= endSolution.getTime();
        };
    }

    // time in minutes
    private long getTimeHeuristicFunction(Node node1, Node node2) {
        var distance = DistanceCalculator.calculate(node1, node2); //  km
        var speed = TransportType.getFastestTransportSpeed(transportTypesAvailable); //  km/h
        return (long) Math.ceil(distance / (double) speed * 60);
    }
}
