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

    @Autowired
    public ManagerController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/schedule")
    public String schedulePage(@AuthenticationPrincipal User authUser,
                               Model model) {
        var schedule = new TreeSet<>(Comparator.comparing(Trip::getFromTime));
        // todo: page display
        schedule.addAll(tripService.findAll().stream()
                .limit(30)
                .collect(Collectors.toSet()));
        var frontendData = new HashMap<String, Object>();
        frontendData.put("schedule", schedule);
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "manager/schedule";
    }

}
