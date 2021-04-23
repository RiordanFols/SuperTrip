package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.domain.TicketStatus;
import ru.chernov.diplom.domain.entity.Solution;
import ru.chernov.diplom.domain.entity.Ticket;
import ru.chernov.diplom.domain.entity.User;
import ru.chernov.diplom.repository.TicketRepository;

import java.util.UUID;

/**
 * @author Pavel Chernov
 */
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

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

    public Ticket assembleAndSave(Solution solution, User user) {
        var trips = solution.getTrips();
        Ticket ticket = new Ticket();
        ticket.setNumber(UUID.randomUUID().toString());
        ticket.setStatus(TicketStatus.NOT_PAID);
        ticket.setTrips(trips);
        ticket.setPassengerName(user.getName());
        ticket.setPassengerSurname(user.getSurname());
        ticket.setPassengerMiddleName(user.getMiddleName());
        ticket.setPassengerPassportId(user.getPassportId());
        ticket.setPassengerPassportSeries(user.getPassportSeries());
        return save(ticket);
    }

    public Ticket assembleAndSave(Solution solution, String userName, String userSurname,
                                  String userMiddleName, int userPassportId, int userPassportSeries) {
        var trips = solution.getTrips();
        Ticket ticket = new Ticket();
        ticket.setNumber(UUID.randomUUID().toString());
        ticket.setStatus(TicketStatus.NOT_PAID);
        ticket.setTrips(trips);
        ticket.setPassengerName(userName);
        ticket.setPassengerSurname(userSurname);
        ticket.setPassengerMiddleName(userMiddleName);
        ticket.setPassengerPassportId(userPassportId);
        ticket.setPassengerPassportSeries(userPassportSeries);
        return save(ticket);
    }

    public void pay(String ticketNumber) {
        Ticket ticket = findByNumber(ticketNumber);
        if (ticket.getStatus().equals(TicketStatus.NOT_PAID))
            ticket.setStatus(TicketStatus.PAID);

        save(ticket);
    }
}
