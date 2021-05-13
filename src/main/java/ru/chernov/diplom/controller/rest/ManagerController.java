package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chernov.diplom.domain.entity.Trip;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.service.TripService;
import ru.chernov.diplom.service.UserService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("/manager")
@PreAuthorize("hasAuthority('MANAGER')")
public class ManagerController {

    private final TripService tripService;
    private final UserService userService;

    @Autowired
    public ManagerController(TripService tripService, UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    @GetMapping("/schedule")
    public String schedulePage(@AuthenticationPrincipal User authUser,
                               Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var schedule = new TreeSet<>(Comparator.comparing(Trip::getFromTime));

        schedule.addAll(tripService.findAll().stream()
                .limit(30)
                .collect(Collectors.toSet()));
        var frontendData = new HashMap<String, Object>();
        frontendData.put("schedule", schedule);
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "manager/schedule";
    }

    @GetMapping("/users")
    public String usersInfoPage(@AuthenticationPrincipal User authUser,
                            Model model) {
        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        frontendData.put("users", userService.findAll().stream()
                .limit(30)
                .collect(Collectors.toSet()));
        model.addAttribute("frontendData", frontendData);
        return "manager/users";
    }

    @GetMapping("/ticket/search")
    public String ticketSearchPage(@AuthenticationPrincipal User authUser,
                                   Model model) {
        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "manager/ticket_search";
    }
}
