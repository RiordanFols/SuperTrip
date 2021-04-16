package ru.chernov.diplom.alg.solver;

import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
public class DijkstraSolver extends Solver{

    public DijkstraSolver(Schedule schedule, Node start, Node end,
                          LocalDateTime startTime, LocalDateTime endTime, int maxTransfersNumber,
                          Set<TransportType> transportTypesAvailable, SolutionType solutionType) {
        super(schedule, start, end, startTime, endTime, maxTransfersNumber, transportTypesAvailable, solutionType);
    }

    @Override
    public void solve() {

    }
}
