package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.chernov.diplom.alg.solver.SolutionType;
import ru.chernov.diplom.component.FormChecker;
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

    private final SolutionService solutionService;
    private final UserService userService;
    private final FormChecker formChecker;

    @Autowired
    public MainController(SolutionService solutionService, UserService userService, FormChecker formChecker) {
        this.solutionService = solutionService;
        this.userService = userService;
        this.formChecker = formChecker;
    }

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal User authUser,
                           @RequestParam(required = false) String error,
                           @RequestParam(required = false) String notification,
                           @RequestParam(required = false) String fromCity,
                           @RequestParam(required = false) String toCity,
                           @RequestParam(required = false) LocalDateTime departureTime,
                           @RequestParam(required = false) LocalDateTime arrivalTime,
                           @RequestParam(required = false, defaultValue = "true") boolean busAllowed,
                           @RequestParam(required = false, defaultValue = "true") boolean trainAllowed,
                           @RequestParam(required = false, defaultValue = "true") boolean planeAllowed,
                           Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        var formData = new HashMap<>() {{
            put("fromCity", fromCity);
            put("toCity", toCity);
            put("departureTime", departureTime);
            put("arrivalTime", arrivalTime);
            put("busAllowed", busAllowed);
            put("trainAllowed", trainAllowed);
            put("planeAllowed", planeAllowed);
        }};
        frontendData.put("formData", formData);
        frontendData.put("authUser", authUser);
        frontendData.put("error", error);
        frontendData.put("notification", notification);
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
                                @RequestParam(required = false) boolean busAllowed,
                                @RequestParam(required = false) boolean trainAllowed,
                                @RequestParam(required = false) boolean planeAllowed,
                                RedirectAttributes ra, Model model) {

        var error = formChecker.checkRouteSearchForm(fromCity, toCity,
                departureTime, arrivalTime, busAllowed, trainAllowed, planeAllowed);

        if (error != null) {
            ra.addAttribute("error", error);
            ra.addAttribute("fromCity", fromCity);
            ra.addAttribute("toCity", toCity);
            ra.addAttribute("departureTime", departureTime);
            ra.addAttribute("arrivalTime", arrivalTime);
            ra.addAttribute("busAllowed", busAllowed);
            ra.addAttribute("trainAllowed", trainAllowed);
            ra.addAttribute("planeAllowed", planeAllowed);
            return "redirect:/";
        }

        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var solution1 = solutionService.findSolution(busAllowed, trainAllowed, planeAllowed,
                departureTime, arrivalTime, fromCity, toCity, SolutionType.TIME);
        var solution2 = solutionService.findSolution(busAllowed, trainAllowed, planeAllowed,
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
