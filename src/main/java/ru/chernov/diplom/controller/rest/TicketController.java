package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chernov.diplom.domain.Role;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.service.SolutionService;
import ru.chernov.diplom.service.TicketService;

import java.util.HashMap;

/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final SolutionService solutionService;

    @Autowired
    public TicketController(TicketService ticketService, SolutionService solutionService) {
        this.ticketService = ticketService;
        this.solutionService = solutionService;
    }

    @GetMapping("/assemble/{id}")
    public String assembleTicketPage(@AuthenticationPrincipal User authUser,
                                     @PathVariable(name = "id") long solutionId,
                                     Model model) {
        if (authUser != null && authUser.getRoles().contains(Role.USER)) {
            var solution = solutionService.findById(solutionId);
            Ticket ticket = ticketService.assembleAndSave(solution, authUser);
            return "redirect:/ticket/buy/" + ticket.getNumber();
        } else {
            var frontendData = new HashMap<String, Object>();
            frontendData.put("solutionId", solutionId);
            model.addAttribute("frontendData", frontendData);
            return "passenger_info_blank";
        }
    }

    @PostMapping("/assemble/{id}")
    public String assembleTicket(@PathVariable(name = "id") long solutionId,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String middleName,
                                 @RequestParam int passportId,
                                 @RequestParam int passportSeries) {

        var solution = solutionService.findById(solutionId);
        var passenger = new User(name, surname, middleName, passportId, passportSeries);
        Ticket ticket = ticketService.assembleAndSave(solution, passenger);
        return "redirect:/ticket/buy/" + ticket.getNumber();
    }

    @GetMapping("/buy/{number}")
    public String buyTicketPage(@PathVariable(name = "number") String ticketNumber,
                                Model model) {
        var frontendData = new HashMap<String, Object>();
        frontendData.put("ticketNumber", ticketNumber);
        model.addAttribute("frontendData", frontendData);
        return "payment";
    }

    @PostMapping("/buy/{number}")
    public String buyTicket(@PathVariable(name = "number") String ticketNumber) {
        ticketService.pay(ticketNumber);
        return "redirect:/main";
    }

    @GetMapping("/info/{number}")
    public String ticketInfoPage(@PathVariable(name = "number") String ticketNumber,
                                 Model model) {
        Ticket ticket = ticketService.findByNumber(ticketNumber);
        var frontendData = new HashMap<String, Object>();
        frontendData.put("ticket", ticket);
        model.addAttribute("frontendData", frontendData);

        return "ticket_info";
    }

    @GetMapping("/search")
    public String ticketSearchPage() {
        return "tickets_search";
    }

    @PostMapping("/search")
    public String ticketSearch(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String middleName,
                               @RequestParam int passportId,
                               @RequestParam int passportSeries,
                               Model model) {
        var passenger = new User(name, surname, middleName, passportId, passportSeries);
        var passengerTickets = ticketService.findTicketsByUser(passenger);

        var frontendData = new HashMap<String, Object>();
        frontendData.put("user", passenger);
        frontendData.put("expiredTickets", ticketService.getExpiredTickets(passengerTickets));
        frontendData.put("actualTickets", ticketService.getActualTickets(passengerTickets));
        model.addAttribute("frontendData", frontendData);
        return "tickets_search_result";
    }
}
