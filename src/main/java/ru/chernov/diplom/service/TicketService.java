package ru.chernov.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private final SolutionService solutionService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, SolutionService solutionService) {
        this.ticketRepository = ticketRepository;
        this.solutionService = solutionService;
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket findByNumber(String number) {
        return ticketRepository.findByNumber(number);
    }

    public Ticket assembleAndSave(long solutionId, User user) {
        // todo: error in case solution was deleted
        var trips = solutionService.findById(solutionId).getTrips();
        Ticket ticket = new Ticket();
        ticket.setNumber(UUID.randomUUID().toString());
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
        ticket.setTrips(trips);
        ticket.setPassengerName(userName);
        ticket.setPassengerSurname(userSurname);
        ticket.setPassengerMiddleName(userMiddleName);
        ticket.setPassengerPassportId(userPassportId);
        ticket.setPassengerPassportSeries(userPassportSeries);
        return save(ticket);
    }
}
