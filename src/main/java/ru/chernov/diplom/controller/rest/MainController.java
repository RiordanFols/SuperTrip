package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.alg.solver.DijkstraSolver;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.service.NodeService;
import ru.chernov.diplom.service.SolutionService;
import ru.chernov.diplom.service.TicketService;
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
    private final TicketService ticketService;
    private final SolutionService solutionService;

    @Autowired
    public MainController(TripService tripService, NodeService nodeService,
                          TicketService ticketService, SolutionService solutionService) {
        this.tripService = tripService;
        this.nodeService = nodeService;
        this.ticketService = ticketService;
        this.solutionService = solutionService;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    // todo: remove logic from controller
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
        Schedule schedule = new Schedule(tripService.getAll());
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
        DijkstraSolver solver1 = new DijkstraSolver(
                schedule, start, end, departureTime, arrivalTime, transfersN, transportTypes,
                SolutionType.TIME);
        DijkstraSolver solver2 = new DijkstraSolver(
                schedule, start, end, departureTime, arrivalTime, transfersN, transportTypes,
                SolutionType.COST);
        Solution solution1 = solver1.solve();
        Solution solution2 = solver2.solve();
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

    @GetMapping("ticket/buy/{id}")
    public String buyTicketPage(@PathVariable(name = "id") long solutionId,
                                Model model) {
        var frontendData = new HashMap<String, Object>();
        frontendData.put("solutionId", solutionId);
        model.addAttribute("frontendData", frontendData);
        return "passenger_info_blank";
    }

    @PostMapping("ticket/buy/{id}")
    public String assembleTicket(@PathVariable(name = "id") long solutionId,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String middleName,
                                 @RequestParam int passportId,
                                 @RequestParam int passportSeries) {

        // todo: error in case solution was deleted
        var solution = solutionService.findById(solutionId);
        ticketService.assembleAndSave(solution,
                name, surname, middleName, passportId, passportSeries);
        return "redirect:/main";
    }

}
