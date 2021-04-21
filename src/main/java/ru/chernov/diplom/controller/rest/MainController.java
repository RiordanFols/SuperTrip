package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.alg.Schedule;
import ru.chernov.diplom.alg.Solution;
import ru.chernov.diplom.alg.solver.DijkstraSolver;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Node;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.service.NodeService;
import ru.chernov.diplom.service.TicketService;
import ru.chernov.diplom.service.TripService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Controller
public class MainController {

    private final TripService tripService;
    private final NodeService nodeService;
    private final TicketService ticketService;

    @Autowired
    public MainController(TripService tripService, NodeService nodeService, TicketService ticketService) {
        this.tripService = tripService;
        this.nodeService = nodeService;
        this.ticketService = ticketService;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/main")
    public String findSolution(@RequestParam String fromCity,
                               @RequestParam String toCity,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       LocalDateTime departureTime,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       LocalDateTime arrivalTime,
                               @RequestParam(required = false) boolean busAvailable,
                               @RequestParam(required = false) boolean trainAvailable,
                               @RequestParam(required = false) boolean planeAvailable,
                               @RequestParam int transfersN,
                               @RequestParam String solutionType,
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
        DijkstraSolver solver = new DijkstraSolver(
                schedule, start, end, departureTime, arrivalTime, transfersN, transportTypes,
                SolutionType.valueOf(solutionType.toUpperCase()));
        Solution solution = solver.solve();
        Ticket ticket = ticketService.registerTempTicket(solution);

        var frontendData = new HashMap<String, Object>();
        frontendData.put("tempTicketNumber", ticket.getNumber());
        model.addAttribute("frontendData", frontendData);
        return "passenger_info_blank";
    }

    @PostMapping("ticket/assemble")
    public String assembleTicket(@RequestParam String tempTicketNumber,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String middleName,
                                 @RequestParam int passportId,
                                 @RequestParam int passportSeries) {
        ticketService.assembleAndSave(tempTicketNumber,
                name, surname, middleName, passportId, passportSeries);
        return "redirect:/main";
    }

}
