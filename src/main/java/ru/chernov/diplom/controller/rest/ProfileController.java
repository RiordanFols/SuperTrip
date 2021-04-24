package ru.chernov.diplom.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.service.TicketService;
import ru.chernov.diplom.service.UserService;

import java.util.HashMap;

/**
 * @author Pavel Chernov
 */
@Controller
public class ProfileController {

    private final UserService userService;
    private final TicketService ticketService;

    @Autowired
    public ProfileController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User authUser,
                              Model model) {
        var frontendData = new HashMap<String, Object>();
        frontendData.put("user", authUser);
        var userTickets = ticketService.findTicketsByUser(authUser);
        frontendData.put("expiredTickets", ticketService.getExpiredTickets(userTickets));
        frontendData.put("actualTickets", ticketService.getActualTickets(userTickets));
        model.addAttribute("frontendData", frontendData);
        return "auth/profile";
    }
}