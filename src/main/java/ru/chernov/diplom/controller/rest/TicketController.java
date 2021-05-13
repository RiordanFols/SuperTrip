package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.chernov.diplom.component.FormChecker;
import ru.chernov.diplom.domain.Role;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.service.SolutionService;
import ru.chernov.diplom.service.TicketService;
import ru.chernov.diplom.service.UserService;

import java.util.HashMap;

/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final SolutionService solutionService;
    private final UserService userService;
    private final FormChecker formChecker;

    @Autowired
    public TicketController(TicketService ticketService, SolutionService solutionService,
                            UserService userService, FormChecker formChecker) {
        this.ticketService = ticketService;
        this.solutionService = solutionService;
        this.userService = userService;
        this.formChecker = formChecker;
    }

    @GetMapping("/assemble/{id}")
    public String assembleTicketPage(@AuthenticationPrincipal User authUser,
                                     @PathVariable(name = "id") long solutionId,
                                     @RequestParam(required = false) String error,
                                     @RequestParam(required = false) String notification,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String surname,
                                     @RequestParam(required = false) String middleName,
                                     @RequestParam(required = false) String passportId,
                                     @RequestParam(required = false) String passportSeries,
                                     Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        if (authUser != null && authUser.getRoles().contains(Role.USER)) {
            var solution = solutionService.findById(solutionId);
            Ticket ticket = ticketService.assembleAndSave(solution, authUser);
            return "redirect:/ticket/buy/" + ticket.getNumber();
        } else {
            var frontendData = new HashMap<String, Object>();
            var formData = new HashMap<>() {{
                put("name", name);
                put("surname", surname);
                put("middleName", middleName);
                put("passportId", passportId);
                put("passportSeries", passportSeries);
            }};
            frontendData.put("formData", formData);
            frontendData.put("error", error);
            frontendData.put("notification", notification);
            frontendData.put("solutionId", solutionId);
            frontendData.put("authUser", authUser);
            model.addAttribute("frontendData", frontendData);
            return "passenger_info_blank";
        }
    }

    // todo: unknown bug. Double values of RequestParam from form.
    @PostMapping("/assemble/{id}")
    public String assembleTicket(@PathVariable(name = "id") long solutionId,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String middleName,
                                 @RequestParam String passportId,
                                 @RequestParam String passportSeries,
                                 RedirectAttributes ra) {
        var error = formChecker.checkUserData(name, surname, middleName, passportId, passportSeries);
        if (error != null) {
            ra.addAttribute("error", error);
            ra.addAttribute("name", name);
            ra.addAttribute("surname", surname);
            ra.addAttribute("middleName", middleName);
            ra.addAttribute("passportId", passportId);
            ra.addAttribute("passportSeries", passportSeries);
            return "redirect:/ticket/assemble/" + solutionId;
        }

        var solution = solutionService.findById(solutionId);
        var passenger = new User(name, surname, middleName,
                Integer.parseInt(passportId), Integer.parseInt(passportSeries));
        Ticket ticket = ticketService.assembleAndSave(solution, passenger);
        return "redirect:/ticket/buy/" + ticket.getNumber();
    }

    @GetMapping("/buy/{number}")
    public String buyTicketPage(@AuthenticationPrincipal User authUser,
                                @PathVariable(name = "number") String ticketNumber,
                                Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        frontendData.put("ticket", ticketService.findByNumber(ticketNumber));
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
        return "payment";
    }

    @PostMapping("/buy/{number}")
    public String buyTicket(@AuthenticationPrincipal User authUser,
                            @PathVariable(name = "number") String ticketNumber) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        ticketService.pay(ticketNumber, authUser);
        return "redirect:/";
    }

    @GetMapping("/info/{number}")
    public String ticketInfoPage(@AuthenticationPrincipal User authUser,
                                 @PathVariable(name = "number") String ticketNumber,
                                 Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        Ticket ticket = ticketService.findByNumber(ticketNumber);
        var frontendData = new HashMap<String, Object>();
        frontendData.put("ticket", ticket);
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);

        return "ticket_info";
    }

    @GetMapping("/search")
    public String ticketSearchPage(@AuthenticationPrincipal User authUser,
                                   Model model) {
        if (authUser != null)
            authUser = userService.findById(authUser.getId());

        var frontendData = new HashMap<String, Object>();
        frontendData.put("authUser", authUser);
        model.addAttribute("frontendData", frontendData);
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
