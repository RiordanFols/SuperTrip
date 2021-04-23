package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chernov.diplom.domain.entity.Ticket;
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
    public String assembleTicketPage(@PathVariable(name = "id") long solutionId,
                                     Model model) {
        var frontendData = new HashMap<String, Object>();
        frontendData.put("solutionId", solutionId);
        model.addAttribute("frontendData", frontendData);
        return "passenger_info_blank";
    }

    @PostMapping("/assemble/{id}")
    public String assembleTicket(@PathVariable(name = "id") long solutionId,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String middleName,
                                 @RequestParam int passportId,
                                 @RequestParam int passportSeries) {

        var solution = solutionService.findById(solutionId);
        Ticket ticket = ticketService.assembleAndSave(solution,
                name, surname, middleName, passportId, passportSeries);
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
}
