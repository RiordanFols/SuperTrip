package ru.chernov.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.chernov.diplom.domain.entity.Ticket;

/**
 * @author Pavel Chernov
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByNumber(String number);

    @Query(nativeQuery = true, value = "SELECT * FROM ticket t WHERE (" +
            "t.pas_name =?1 AND " +
            "t.pas_surname =?2 AND " +
            "t.pas_middle_name=?3 AND " +
            "t.pas_passport_id=?4 AND " +
            "t.pas_passport_series=?5)")
    Ticket findAllByUserInfo(String name, String surname, String middleName, int passportId, int passportSeries);
}
