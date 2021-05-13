package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.alg.solver.Solver;
import ru.chernov.diplom.alg.solver.SolverFactory;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.repository.SolutionRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    public Solution findSolution(boolean busAvailable, boolean trainAvailable, boolean planeAvailable,
                                 LocalDateTime departureTime, LocalDateTime arrivalTime,
                                 String fromCity, String toCity, SolutionType solutionType) {
        Schedule schedule = new Schedule(tripService.findAll());
        Node start = nodeService.findByName(fromCity);
        Node end = nodeService.findByName(toCity);
        // todo: open-closed P
        Set<TransportType> transportTypes = new HashSet<>() {{
            if (busAvailable)
                add(TransportType.BUS);
            if (trainAvailable)
                add(TransportType.TRAIN);
            if (planeAvailable)
                add(TransportType.PLANE);
        }};
        Solver solver = SolverFactory.getAppropriateSolver(schedule, start, end,
                departureTime, arrivalTime, transportTypes, solutionType);
        return solver.solve();
    }
}
