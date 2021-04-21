package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernov.diplom.domain.entity.Ticket;

/**
 * @author Pavel Chernov
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByNumber(String number);
}
