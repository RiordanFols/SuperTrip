package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.TicketStatus;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
    }

    public Ticket save(Ticket ticket) {
        ticket.setCreationDateTime(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket findByNumber(String number) {
        return ticketRepository.findByNumber(number);
    }

    public List<Ticket> findTicketsByUser(User user) {
        return ticketRepository.findAllByUserInfo(user.getName(), user.getSurname(),
                user.getMiddleName(), user.getPassportId(), user.getPassportSeries());
    }

    public Ticket assembleAndSave(Solution solution, User user) {
        var trips = solution.getTrips();
        Ticket ticket = new Ticket();
        ticket.setNumber(UUID.randomUUID().toString());
        ticket.setStatus(TicketStatus.NOT_PAID);
        ticket.setTrips(trips);
        ticket.setPasName(user.getName());
        ticket.setPasSurname(user.getSurname());
        ticket.setPasMiddleName(user.getMiddleName());
        ticket.setPasPassportId(user.getPassportId());
        ticket.setPasPassportSeries(user.getPassportSeries());
        return save(ticket);
    }

    public void pay(String ticketNumber, User authUser) {
        Ticket ticket = findByNumber(ticketNumber);
        if (ticket.getStatus().equals(TicketStatus.NOT_PAID))
            ticket.setStatus(TicketStatus.PAID);
        if (authUser != null)
            authUser.addToSpent(ticket.getCost());

        save(ticket);
        userService.save(authUser);
    }

    public List<Ticket> getActualTickets(List<Ticket> tickets) {
        return tickets.stream()
                .filter(e -> {
                    var lastTrip = e.getTrips().get(e.getTrips().size() - 1);
                    return lastTrip.getFromTime().isAfter(LocalDateTime.now());
                })
                .collect(Collectors.toList());
    }

    public List<Ticket> getExpiredTickets(List<Ticket> tickets) {
        return tickets.stream()
                .filter(e -> {
                    var lastTrip = e.getTrips().get(e.getTrips().size() - 1);
                    return lastTrip.getFromTime().isBefore(LocalDateTime.now());
                })
                .collect(Collectors.toList());
    }
}
