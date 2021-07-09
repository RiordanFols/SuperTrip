package ru.chernov.supertrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.supertrip.alg.Schedule;
import ru.chernov.supertrip.alg.solver.SolutionType;
import ru.chernov.supertrip.alg.solver.Solver;
import ru.chernov.supertrip.alg.solver.SolverFactory;
import ru.chernov.supertrip.domain.TransportType;
import ru.chernov.supertrip.domain.entity.Node;
import ru.chernov.supertrip.domain.entity.Solution;
import ru.chernov.supertrip.repository.SolutionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pavel Chernov
 */
@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final TripService tripService;
    private final NodeService nodeService;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository, TripService tripService, NodeService nodeService) {
        this.solutionRepository = solutionRepository;
        this.tripService = tripService;
        this.nodeService = nodeService;
    }

    public void save(Solution solution) {
        if (solution != null) {
            solution.setCreationDateTime(LocalDateTime.now());
            solutionRepository.save(solution);
        }
    }

    public void giveFakeId(Solution solution) {
        solution.setId(new Random().nextInt(1_000_000) + 1_000_000);
    }

    public Solution findById(long id) {
        return solutionRepository.findById(id).orElse(null);
    }

    public List<Solution> findAllSolutions(Map<TransportType, Boolean> transportMap,
                                           LocalDateTime departureTime, LocalDateTime arrivalTime,
                                           String fromCity, String toCity) {
        Schedule schedule = new Schedule(tripService.findAll());
        Node start = nodeService.findByName(fromCity);
        Node end = nodeService.findByName(toCity);

        var transportAvailable = new HashSet<TransportType>();
        for (var type : transportMap.keySet()) {
            if (transportMap.get(type))
                transportAvailable.add(type);
        }

        Solver solver1 = SolverFactory.getProperSolver(schedule, start, end,
                departureTime, arrivalTime, transportAvailable, SolutionType.TIME);
        Solver solver2 = SolverFactory.getProperSolver(schedule, start, end,
                departureTime, arrivalTime, transportAvailable, SolutionType.COST);

        Solution solution1 = solver1.solve();
        Solution solution2 = solver2.solve();

        if (solution1 == null || solution2 == null)
            return Arrays.asList(solution1, null, null, solution2);

        Solver solver3 = SolverFactory.getProperSolver(schedule, start, end, departureTime, arrivalTime,
                transportAvailable, SolutionType.TIME_OPTIMAL, solution2.getCost(), solution1.getCost());
        Solver solver4 = SolverFactory.getProperSolver(schedule, start, end, departureTime, arrivalTime,
                transportAvailable, SolutionType.COST_OPTIMAL, solution1.getTime(), solution2.getTime());

        Solution solution3 = solver3.solve();
        Solution solution4 = solver4.solve();

        return Stream.of(solution1, solution3, solution4, solution2)
                .map(e -> e == null ? new Solution() : e)
                .collect(Collectors.toList());
    }
}
