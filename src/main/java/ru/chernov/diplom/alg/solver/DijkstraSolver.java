package ru.chernov.diplom.alg.solver;

import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.domain.entity.Trip;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Pavel Chernov
 */
public class DijkstraSolver extends Solver {

    public DijkstraSolver(Schedule schedule, Node start, Node end,
                          LocalDateTime startTime, LocalDateTime endTime,
                          Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);

    }

    @Override
    public Solution solve() {

        // finding the straight way from start to nodes
        findStraightSolutions();

        while (undone.size() > 1) {
            // node with minimum weight from undone
            Node minNode = getMinNode();
            if (minNode == null)
                break;
            undone.remove(minNode);
            if (checkIfRouteTooBig(minNode))
                continue;

            for (Node curNode : undone) {
                // if edge between nodes exists
                if (schedule.isEdgePresent(minNode, curNode)) {
                    Set<Trip> edgeTrips = schedule.findTripsByNodes(minNode, curNode);
                    Trip lastTrip = solutions.get(minNode).getLastTrip();
                    Trip plannedTrip = findBestTrip(edgeTrips, lastTrip, lastTrip.getToTime(), endTime);
                    // if found satisfying trip
                    if (plannedTrip != null)
                        algorithmIteration(plannedTrip, minNode, curNode);
                }
            }
        }
//        optimizeSolution();

//        printResult();

        return solutions.get(end);
    }

    // finding the straight way from start to nodes
    private void findStraightSolutions() {
        for (Node curNode : undone) {
            // if edge between start end node exists
            if (schedule.isEdgePresent(start, curNode)) {
                Set<Trip> edgeTrips = schedule.findTripsByNodes(start, curNode);
                Trip firstTrip = findBestTrip(edgeTrips, null, startTime, endTime);
                // if can't find any satisfying trip
                if (firstTrip == null)
                    return;

                Solution solution = new Solution();
                solution.getTrips().add(firstTrip);
                solution.setTime(firstTrip.getTravelTime());
                solution.setCost(firstTrip.getCost());
                solution.setType(solutionType);

                solutions.put(curNode, solution);
            } else {
                solutions.put(curNode, null);
            }
        }
    }

    // finding the first(for time) and the best satisfying trip
    private Trip findBestTrip(Set<Trip> trips, Trip lastTrip, LocalDateTime min, LocalDateTime max) {
        Predicate<Trip> timeFilterPredicate = (e) -> {
            var fromDateTime = e.getFromTime();
            var toDateTime = e.getToTime();
            var minThreshold = min;
            if (lastTrip != null) {
                var lastNode = lastTrip.getEdge().getTo();
                // minimum time for transfer from one transport type to another
                var minTransferTime = lastNode.getTransferTime(e.getType(), lastTrip.getType());
                minThreshold = minThreshold.plusMinutes(minTransferTime);
            }
            return fromDateTime.isAfter(minThreshold) || fromDateTime.isEqual(minThreshold) &&
                    toDateTime.isBefore(max) || toDateTime.isEqual(max);
        };
        Trip minCostTrip = trips.stream()
                .filter(timeFilterPredicate)
                .min(Comparator.comparing(Trip::getCost)).orElse(null);
        var minCost = minCostTrip != null ? minCostTrip.getCost() : 0;

        return switch (solutionType) {
            case TIME_ABSOLUTE -> trips.stream()
                    .filter(timeFilterPredicate)
                    .min(Comparator.comparing(Trip::getToTime)).orElse(null);
            case COST_ABSOLUTE -> trips.stream()
                    .filter(timeFilterPredicate)
                    .filter(e -> e.getCost() == minCost)
                    .min(Comparator.comparing(Trip::getToTime)).orElse(null);
            default -> throw new IllegalArgumentException("Wrong solutionType");
        };
    }

    // get node with minimum weight from undone list
    private Node getMinNode() {
        return switch (solutionType) {
            case TIME_ABSOLUTE -> undone.stream()
                    .filter(node -> solutions.get(node) != null)
                    .min(Comparator.comparingDouble(o -> solutions.get(o).getTime())).orElse(null);
            case COST_ABSOLUTE -> undone.stream()
                    .filter(node -> solutions.get(node) != null)
                    .min(Comparator.comparingDouble(o -> solutions.get(o).getCost())).orElse(null);
            default -> throw new IllegalArgumentException("Wrong solutionType");
        };
    }

    public boolean checkIfRouteTooBig(Node minNode) {
        var minNodeSolution = solutions.get(minNode);
        var endSolution = solutions.get(end);
        // if can't compare solutions
        if (minNodeSolution == null || endSolution == null)
            return false;

        switch (solutionType) {
            case COST_ABSOLUTE -> {
                return minNodeSolution.getCost() >= endSolution.getCost();
            }
            case TIME_ABSOLUTE -> {
                return minNodeSolution.getTime() >= endSolution.getTime();
            }
            default -> {
                return false;
            }
        }
    }

    private void algorithmIteration(Trip plannedTrip, Node minNode, Node curNode) {
        var solution = solutions.get(minNode);
        var lastTrip = solution.getLastTrip();
        solutions.put(curNode, checkNewSolution(curNode, solution, lastTrip, plannedTrip));
    }

    private Solution checkNewSolution(Node curNode, Solution solution, Trip lastTrip, Trip plannedTrip) {

        // newTime = current time on road + transfer time + trip time
        var newTime = solution.getTime() + plannedTrip.getTravelTime() +
                ChronoUnit.MINUTES.between(lastTrip.getToTime(), plannedTrip.getFromTime());
        var newCost = solution.getCost() + plannedTrip.getCost();

        // the best solution for chosen node
        var curSolution = solutions.get(curNode);

        var oldWeight = 0.0;
        var newWeight = 0.0;
        switch (solutionType) {
            case TIME_ABSOLUTE -> {
                oldWeight = curSolution == null ? 0 : curSolution.getTime();
                newWeight = newTime;
            }
            case COST_ABSOLUTE -> {
                oldWeight = curSolution == null ? 0 : curSolution.getCost();
                newWeight = newCost;
            }
        }
        // if there is no solution yet or new solution is better
        if (curSolution == null || newWeight < oldWeight) {
            curSolution = new Solution();
            curSolution.setTime(newTime);
            curSolution.setCost(newCost);
            curSolution.setType(solutionType);
            curSolution.setTrips(new ArrayList<>(solution.getTrips()));
            curSolution.getTrips().add(plannedTrip);
        }
        return curSolution;
    }

    private void optimizeSolution() {
        if (solutionType == SolutionType.TIME_ABSOLUTE)
            optimizeTime();
    }

    // searching for the opportunity of a later departure with the same arrival (i.e. saving time)
    private void optimizeTime() {
        for (Node key : solutions.keySet()) {
            var solution = solutions.get(key);
            var trips = solution.getTrips();
            // going throw trips from the last to 2nd
            for (int i = trips.size() - 1; i > 0; i--) {
                // finding max time when passenger can depart and not be late for next trip
                var curTrip = trips.get(i);
                var prevTrip = trips.get(i - 1);
                var minTransferTime = curTrip.getEdge().getFrom().getTransferTime(prevTrip.getType(), curTrip.getType());
                var maxArrivalTime = curTrip.getFromTime().minusMinutes(minTransferTime);
                // finding earlier transport for the previous trip
                // to reduce time between two trips
                Trip bestTrip = schedule.findTripsByEdge(prevTrip.getEdge()).stream()
                        .filter(e -> e.getType().equals(prevTrip.getType()))
                        .filter(e -> e.getToTime().isBefore(maxArrivalTime) || e.getToTime().isEqual(maxArrivalTime))
                        .filter(e -> !e.equals(prevTrip))
                        .min(Comparator.comparingLong(o -> ChronoUnit.MINUTES.between(maxArrivalTime, o.getToTime())))
                        .orElse(null);

                if (bestTrip != null) {
                    solution.getTrips().set(i - 1, bestTrip);
                    solutions.put(key, solution);
                }
            }
        }
    }

    public void printResult() {
        System.out.println("\n\nResult:");
        var solution = solutions.get(end);
        if (solution != null) {
            var time = solution.getTime();
            var cost = solution.getCost();
            var trips = solution.getTrips();

            System.out.println("Time = " + time / 60 + "h " + time % 60 + "m");
            System.out.println("Cost = " + cost + "$");
            System.out.println("Route:");
            trips.forEach(System.out::println);
        } else {
            System.out.println("No route");
        }
    }
}
