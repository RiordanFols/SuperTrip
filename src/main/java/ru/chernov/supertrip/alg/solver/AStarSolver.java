package ru.chernov.supertrip.alg.solver;

import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.domain.entity.Solution;
import ru.chernov.supertrip.domain.entity.Trip;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Алгоритм А* работет следующим образом:
 * 1) Сначала от начальной точки прокладываются решения ко всем
 * точкам, к которым есть маршрут (метод findStraightSolutions)
 * 2) В цикле происходит выбор пункта с минимальной стоимостью
 * (то есть до которого можно добраться быстрее всего от старта),
 * один и тот же пункт не может быть выбран дважды. (метод getMinNode)
 * 3) От данного пунтка просчитываюся маршруты ко всем соседним еще не задействованным.
 * ... пункты 2 и 3 повторяются, пока не останется пунтков для выбора в (2)
 * 4) Происходит оптимизация маршрута при возможности (метод optimizeSolutions)
 *
 * @author Pavel Chernov
 */
public class AStarSolver extends Solver {

    public AStarSolver(Schedule schedule, Node start, Node end,
                       LocalDateTime startTime, LocalDateTime endTime,
                       Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        super(schedule, start, end, startTime, endTime, transportTypesAvailable, solutionType);

    }


    @Override
    public Solution solve() {

        // finding the straight way from start to nodes
        findStraightSolutions();

        while (undone.size() > 1) {
//            printIntermediateResult();
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
        optimizeSolution();
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
                    continue;

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
            case TIME -> trips.stream()
                    .filter(timeFilterPredicate)
                    .min(Comparator.comparing(Trip::getToTime)).orElse(null);
            case COST -> trips.stream()
                    .filter(timeFilterPredicate)
                    .filter(e -> e.getCost() == minCost)
                    .min(Comparator.comparing(Trip::getToTime)).orElse(null);
        };
    }

    // get node with minimum weight from undone list
    private Node getMinNode() {
        return switch (solutionType) {
            case TIME -> undone.stream()
                    .filter(node -> solutions.get(node) != null)
                    .min(Comparator.comparingDouble(o -> solutions.get(o).getTime())).orElse(null);
            case COST -> undone.stream()
                    .filter(node -> solutions.get(node) != null)
                    .min(Comparator.comparingDouble(o -> solutions.get(o).getCost())).orElse(null);
        };
    }

    public boolean checkIfRouteTooBig(Node minNode) {
        var minNodeSolution = solutions.get(minNode);
        var endSolution = solutions.get(end);
        // if can't compare solutions
        if (minNodeSolution == null || endSolution == null)
            return false;

        return switch (solutionType) {
            case COST -> minNodeSolution.getCost() >= endSolution.getCost();
            case TIME -> minNodeSolution.getTime() + getTimeHeuristicFunction(minNode, end) >= endSolution.getTime();
        };
    }

    // time in minutes
    public long getTimeHeuristicFunction(Node node1, Node node2) {
        var distance = DistanceCalculator.calculate(node1, node2); //  km
        var speed = TransportType.getFastestTransportSpeed(transportAvailable); //  km/h
        return (long) Math.ceil(distance / (double) speed * 60);
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

//    public void printIntermediateResult() {
//        System.out.println("\n\nIntermediate result:");
//        for (var solutionKey : solutions.keySet()) {
//            var solution = solutions.get(solutionKey);
//            if (solution != null) {
//                var time = solution.getTime();
//                var cost = solution.getCost();
//                var trips = solution.getTrips();
//
//                System.out.println("\n" + solutionKey.getName() + ": ");
//                System.out.println("Time = " + time / 60 + "h " + time % 60 + "m");
//                System.out.println("Cost = " + cost + "$");
//                System.out.println("Route:");
//                trips.forEach(System.out::println);
//            } else {
//                System.out.println("No route");
//            }
//        }
//    }

//    public void printResult() {
//        System.out.println("\n\nResult:");
//        var solution = solutions.get(end);
//        if (solution != null) {
//            var time = solution.getTime();
//            var cost = solution.getCost();
//            var trips = solution.getTrips();
//
//            System.out.println("Time = " + time / 60 + "h " + time % 60 + "m");
//            System.out.println("Cost = " + cost + "$");
//            System.out.println("Route:");
//            trips.forEach(System.out::println);
//        } else {
//            System.out.println("No route");
//        }
//    }
}
