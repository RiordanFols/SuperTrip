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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public Solution save(Solution solution) {
        if (solution != null) {
            solution.setCreationDateTime(LocalDateTime.now());
            return solutionRepository.save(solution);
        }
        return null;
    }

    public Solution findById(long id) {
        return solutionRepository.findById(id).orElse(null);
    }

    public Solution findSolution(Map<TransportType, Boolean> transportMap,
                                 LocalDateTime departureTime, LocalDateTime arrivalTime,
                                 String fromCity, String toCity, SolutionType solutionType) {
        Schedule schedule = new Schedule(tripService.findAll());
        Node start = nodeService.findByName(fromCity);
        Node end = nodeService.findByName(toCity);

        var transportAvailable = new HashSet<TransportType>();
        for (var type : transportMap.keySet()) {
            if (transportMap.get(type))
                transportAvailable.add(type);
        }

        Solver solver = SolverFactory.getAppropriateSolver(schedule, start, end,
                departureTime, arrivalTime, transportAvailable, solutionType);
        return solver.solve();
    }
}
