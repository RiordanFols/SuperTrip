package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.alg.solver.DijkstraSolver;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.service.NodeService;
import ru.chernov.diplom.service.TripService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@RestController
public class TestController {

    private final TripService tripService;
    private final NodeService nodeService;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    public TestController(TripService tripService, NodeService nodeService) {
        this.tripService = tripService;
        this.nodeService = nodeService;
    }

    @GetMapping("/test")
    public void test() {
        Schedule schedule = new Schedule(tripService.getAll());
        Node start = nodeService.findByName("Node1");
        Node end = nodeService.findByName("Node4");
        LocalDateTime startTime = LocalDateTime.parse("01-06-2021 14:00", dtf);
        LocalDateTime endTime = LocalDateTime.parse("03-06-2021 00:00", dtf);
        Set<TransportType> transportTypes = new HashSet<>() {{
            add(TransportType.BUS);
            add(TransportType.PLANE);
        }};
        DijkstraSolver solver = new DijkstraSolver(
                schedule, start, end, startTime, endTime, 3, transportTypes, SolutionType.TIME);
        solver.solve();
    }
}
