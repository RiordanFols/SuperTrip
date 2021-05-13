package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.service.NodeService;
import ru.chernov.diplom.service.SolutionService;
import ru.chernov.diplom.service.TripService;
import ru.chernov.diplom.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Pavel Chernov
 */
@Controller
public class MainController {

    private final TripService tripService;
    private final NodeService nodeService;
    private final SolutionService solutionService;
    private final UserService userService;

    @Autowired
    public MainController(TripService tripService, NodeService nodeService,
                          SolutionService solutionService, UserService userService) {
        this.tripService = tripService;
        this.nodeService = nodeService;
        this.solutionService = solutionService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal User authUser,
                           Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "main";
    }

    @PostMapping("/")
    public String findSolutions(@AuthenticationPrincipal User authUser,
                                @RequestParam String fromCity,
                                @RequestParam String toCity,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        LocalDateTime departureTime,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        LocalDateTime arrivalTime,
                                @RequestParam(required = false) boolean busAvailable,
                                @RequestParam(required = false) boolean trainAvailable,
                                @RequestParam(required = false) boolean planeAvailable,
                                Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var solution1 = solutionService.findSolution(busAvailable, trainAvailable, planeAvailable,
                departureTime, arrivalTime, fromCity, toCity, SolutionType.TIME);
        var solution2 = solutionService.findSolution(busAvailable, trainAvailable, planeAvailable,
                departureTime, arrivalTime, fromCity, toCity, SolutionType.COST);
        
        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        frontendData.put("solutions", new ArrayList<>() {{
            add(solutionService.save(solution1));
            add(solutionService.save(solution2));
        }});
        model.addAttribute("frontendData", frontendData);
        return "route_search_result";
    }


}
