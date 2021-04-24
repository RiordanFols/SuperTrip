package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chernov.diplom.domain.TransportType;
import ru.chernov.diplom.domain.entity.Trip;
import ru.chernov.diplom.service.TripService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    private final TripService tripService;

    @Autowired
    public ManagerController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/schedule")
    public String schedulePage(Model model) {
        var schedule = new TreeSet<>(Comparator.comparing(Trip::getFromTime)) {{
            addAll(tripService.findAll());
        }};
        model.addAttribute("schedule", schedule);
        return "manager/schedule";
    }

    @PostMapping("/schedule")
    public String addTripToSchedule(@RequestParam String from,
                                    @RequestParam String to,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                            LocalDateTime fromTime,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                            LocalDateTime toTime,
                                    @RequestParam String transportType,
                                    @RequestParam int cost) {
        tripService.save(from, to, fromTime, toTime, cost, TransportType.valueOf(transportType.toUpperCase()));
        return "redirect:/manager/schedule";
    }
}
