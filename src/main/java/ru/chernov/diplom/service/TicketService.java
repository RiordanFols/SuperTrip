package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernov.diplom.alg.Solution;
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

    public Ticket assembleAndSave(String tempTicketNumber, User user) {
        Ticket ticket = findByNumber(tempTicketNumber);
        ticket.setPassengerName(user.getName());
        ticket.setPassengerSurname(user.getSurname());
        ticket.setPassengerMiddleName(user.getMiddleName());
        ticket.setPassengerPassportId(user.getPassportId());
        ticket.setPassengerPassportSeries(user.getPassportSeries());
        return save(ticket);
    }

    public Ticket assembleAndSave(String tempTicketNumber, String userName, String userSurname,
                                  String userMiddleName, int userPassportId, int userPassportSeries) {
        Ticket ticket = findByNumber(tempTicketNumber);
        ticket.setPassengerName(userName);
        ticket.setPassengerSurname(userSurname);
        ticket.setPassengerMiddleName(userMiddleName);
        ticket.setPassengerPassportId(userPassportId);
        ticket.setPassengerPassportSeries(userPassportSeries);
        return save(ticket);
    }

    public Ticket registerTempTicket(Solution solution) {
        var tempTicket = new Ticket();
        tempTicket.setNumber(UUID.randomUUID().toString());
        tempTicket.setTrips(solution.getTrips());
        ticketRepository.save(tempTicket);
        return tempTicket;
    }
}
