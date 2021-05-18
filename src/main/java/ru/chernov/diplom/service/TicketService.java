package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.TicketStatus;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.domain.entity.Trip;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final Comparator<Ticket> sortTicketsByFirstTripFromTime = (e1, e2) -> {
        var e1FirstTrip = e1.getTrips().stream()
                .min(Comparator.comparing(Trip::getFromTime)).orElse(null);
        var e2FirstTrip = e2.getTrips().stream()
                .min(Comparator.comparing(Trip::getFromTime)).orElse(null);
        assert e1FirstTrip != null;
        assert e2FirstTrip != null;

        if (e1FirstTrip.getFromTime().isAfter(e2FirstTrip.getFromTime()))
            return 1;
        if (e1FirstTrip.getFromTime().isBefore(e2FirstTrip.getFromTime()))
            return -1;
        return 0;
    };

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(Ticket ticket) {
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
        var cost = trips.stream()
                .map(Trip::getCost)
                .reduce(Double::sum).orElse(0.0);
        var number = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(number, cost, TicketStatus.NOT_PAID, LocalDateTime.now(), trips, user.getName(),
                user.getSurname(), user.getMiddleName(), user.getPassportId(), user.getPassportSeries());
        return save(ticket);
    }

    public void pay(String ticketNumber, User authUser) {
        Ticket ticket = findByNumber(ticketNumber);
        if (ticket.getStatus().equals(TicketStatus.NOT_PAID))
            ticket.setStatus(TicketStatus.PAID);
        if (authUser != null)
            authUser.addToSpent(ticket.getCost());

        save(ticket);
    }

    public List<Ticket> getActualTickets(List<Ticket> tickets) {
        return tickets.stream()
                .filter(e -> {
                    var lastTrip = e.getTrips().get(e.getTrips().size() - 1);
                    return lastTrip.getFromTime().isAfter(LocalDateTime.now());
                })
                .sorted(sortTicketsByFirstTripFromTime)
                .collect(Collectors.toList());
    }

    public List<Ticket> getExpiredTickets(List<Ticket> tickets) {
        return tickets.stream()
                .filter(e -> {
                    var lastTrip = e.getTrips().get(e.getTrips().size() - 1);
                    return lastTrip.getFromTime().isBefore(LocalDateTime.now());
                })
                .sorted(sortTicketsByFirstTripFromTime)
                .collect(Collectors.toList());
    }

}
