package ru.chernov.diplom.alg.solver;

import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.domain.entity.Trip;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author Pavel Chernov
 */
public class DijkstraSolver extends Solver {

    private final List<Node> undone;
    private final Map<Node, Solution> solutions = new HashMap<>();

    public DijkstraSolver(Schedule schedule, Node start, Node end,
                          LocalDateTime startTime, LocalDateTime endTime, int maxTransfersNumber,
                          Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        super(schedule, start, end, startTime, endTime, maxTransfersNumber, transportTypesAvailable, solutionType);

        // filtering trips by time and transport type
        schedule = filterSchedule(this.schedule);

        undone = new ArrayList<>(schedule.getNodes());
        undone.remove(start);
    }

    // todo: bag
    @Override
    public Solution solve() {

        // finding the straight way from start to nodes
        findStraightSolutions();

        while (undone.size() > 1) {
            // node with minimum weight from undone
            Node nearNode = getNearestNode();
            if (nearNode == null)
                break;
            undone.remove(nearNode);

            for (Node curNode : undone) {
                // if edge between nodes exists
                if (schedule.isEdgePresent(nearNode, curNode)) {
                    Trip plannedTrip = findFirstTrip(schedule.findTripsByNodes(nearNode, curNode),
                            solutions.get(nearNode).getLastTrip().getToTime(), endTime);
                    // if found satisfying trip
                    if (plannedTrip != null)
                        algorithmIteration(plannedTrip, nearNode, curNode);
                }
            }
        }
//        optimizeSolution();

        System.out.print("\n\nResult:");
        printResult();

        return solutions.get(end);
    }

    // finding the straight way from start to nodes
    private void findStraightSolutions() {
        for (Node curNode : undone) {
            // if edge between start end node exists
            if (schedule.isEdgePresent(start, curNode)) {
                Trip firstTrip = findFirstTrip(schedule.findTripsByNodes(start, curNode), startTime, endTime);
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

    // finding the first satisfying trip
    private Trip findFirstTrip(Set<Trip> trips, LocalDateTime min, LocalDateTime max) {
        Predicate<Trip> timeFiletPredicate = (e) -> {
            var fromDateTime = e.getFromTime();
            var toDateTime = e.getToTime();
            return fromDateTime.isAfter(min) || fromDateTime.isEqual(min) &&
                    toDateTime.isBefore(max) || toDateTime.isEqual(max);
        };
        Trip minCostTrip = trips.stream()
                .filter(timeFiletPredicate)
                .min(Comparator.comparing(Trip::getCost)).orElse(null);
        long minCost = minCostTrip != null ? minCostTrip.getCost() : 0;

        return switch (solutionType) {
            case TIME -> trips.stream()
                    .filter(timeFiletPredicate)
                    .min(Comparator.comparing(Trip::getToTime)).orElse(null);
            case COST -> trips.stream()
                    .filter(timeFiletPredicate)
                    .filter(e -> e.getCost() == minCost)
                    .min(Comparator.comparing(Trip::getToTime)).orElse(null);
        };
    }

    // get node with minimum weight from undone list
    private Node getNearestNode() {
        return switch (solutionType) {
            case TIME -> undone.stream()
                    .filter(node -> solutions.get(node) != null)
                    .min(Comparator.comparingLong(o -> solutions.get(o).getTime())).orElse(null);
            case COST -> undone.stream()
                    .filter(node -> solutions.get(node) != null)
                    .min(Comparator.comparingLong(o -> solutions.get(o).getCost())).orElse(null);
        };
    }

    // итерация алгоритма Дейкстры с нахождением ближайшего узла и пересчетом пути до узлов
    private void algorithmIteration(Trip plannedTrip, Node nearNode, Node curNode) {
        var solution = solutions.get(nearNode);
        // stop if number of allowed transfers is reached
        if (solution.getTrips().size() == (maxTransfersNumber + 1))
            return;

        // minimum time for transfer from one transport type to another
        var minTransferTime = nearNode.getTransferTime(
                plannedTrip.getType(),
                solution.getLastTrip().getType());

        var lastTrip = solution.getLastTrip();
        var readyToGo = lastTrip.getToTime().plusMinutes(minTransferTime);
        var departure = plannedTrip.getFromTime();
        // if passenger has time for next trip
        if (readyToGo.isBefore(departure) || readyToGo.equals(departure))
            solutions.put(curNode, checkNewSolution(curNode, solution, lastTrip, plannedTrip));
    }

    private Solution checkNewSolution(Node curNode, Solution solution, Trip lastTrip, Trip plannedTrip) {

        // newTime = current time on road + transfer time + trip time
        var newTime = solution.getTime() + plannedTrip.getTravelTime() +
                ChronoUnit.MINUTES.between(lastTrip.getToTime(), plannedTrip.getFromTime());
        var newCost = solution.getCost() + plannedTrip.getCost();

        // the best solution for chosen node
        var curSolution = solutions.get(curNode);

        long oldWeight = 0;
        long newWeight = 0;
        switch (solutionType) {
            case TIME -> {
                oldWeight = curSolution == null ? 0 : curSolution.getTime();
                newWeight = newTime;
            }
            case COST -> {
                oldWeight = curSolution == null ? 0 : curSolution.getCost();
                newWeight = newCost;
            }
        }
        // if there is no solution yet or new solution is better
        if (curSolution == null || newWeight <= oldWeight) {
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
        if (solutionType == SolutionType.TIME)
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
