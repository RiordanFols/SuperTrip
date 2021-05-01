package ru.chernov.diplom.alg.solver;

import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;

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

        switch (solutionType) {
            case COST -> {
                return minNodeSolution.getCost() >= endSolution.getCost();
            }
            case TIME -> {
                return minNodeSolution.getTime() + getTimeHeuristicFunction(minNode, end) >= endSolution.getTime();
            }
            default -> {
                return false;
            }
        }
    }

    // time in minutes
    private long getTimeHeuristicFunction(Node node1, Node node2) {
        var distance = DistanceCalculator.calculate(node1, node2); //  km
        var speed = TransportType.getFastestTransportSpeed(); //  km/h
        return (long) Math.ceil(distance / (double) speed * 60);
    }
}
