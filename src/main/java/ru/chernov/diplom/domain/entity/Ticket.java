package ru.chernov.diplom.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.chernov.diplom.domain.TicketStatus;

import javax.persistence.*;
import java.util.*;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class Ticket {

    @Id
    @Column(length = 36)
    private String number;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TicketStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticket_trips",
            joinColumns = @JoinColumn(name = "ticket_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "trip_id", nullable = false, updatable = false))
    private List<Trip> trips = new ArrayList<>();

    @Column(length = 25, nullable = false, updatable = false)
    private String passengerName;

    @Column(length = 25, nullable = false, updatable = false)
    private String passengerSurname;

    @Column(length = 25)
    private String passengerMiddleName;

    @Column(nullable = false, updatable = false)
    private int passengerPassportId;

    @Column(nullable = false, updatable = false)
    private int passengerPassportSeries;

    public int getCost() {
        return trips.stream()
                .map(Trip::getCost)
                .reduce(Integer::sum).orElse(0);
    }
}
