package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.alg.solver.DijkstraSolver;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.service.NodeService;
import ru.chernov.diplom.service.SolutionService;
import ru.chernov.diplom.service.TripService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Controller
public class MainController {

    private final TripService tripService;
    private final NodeService nodeService;
    private final SolutionService solutionService;

    @Autowired
    public MainController(TripService tripService, NodeService nodeService, SolutionService solutionService) {
        this.tripService = tripService;
        this.nodeService = nodeService;
        this.solutionService = solutionService;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/main")
    public String findSolutions(@RequestParam String fromCity,
                                @RequestParam String toCity,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        LocalDateTime departureTime,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        LocalDateTime arrivalTime,
                                @RequestParam(required = false) boolean busAvailable,
                                @RequestParam(required = false) boolean trainAvailable,
                                @RequestParam(required = false) boolean planeAvailable,
                                @RequestParam int transfersN,
                                Model model) {
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
        Solution solution1 = new DijkstraSolver(schedule, start, end, departureTime,
                arrivalTime, transfersN, transportTypes, SolutionType.TIME).solve();
        Solution solution2 = new DijkstraSolver(schedule, start, end, departureTime,
                arrivalTime, transfersN, transportTypes, SolutionType.COST).solve();
        solutionService.save(solution1);
        solutionService.save(solution2);

        var frontendData = new HashMap<String, Object>();
        frontendData.put("solutions", new ArrayList<>() {{
            add(solution1);
            add(solution2);
        }});
        model.addAttribute("frontendData", frontendData);
        return "route_search_result";
    }
}
