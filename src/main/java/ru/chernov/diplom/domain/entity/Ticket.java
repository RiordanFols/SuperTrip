package ru.chernov.diplom.domain.entity;

import lombok.Data;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticket_trips",
            joinColumns = @JoinColumn(name = "ticket_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "trip_id", nullable = false, updatable = false))
    private List<Trip> trips = new ArrayList<>();

    @Column(length = 25)
    private String passengerName;

    @Column(length = 25)
    private String passengerSurname;

    @Column(length = 25)
    private String passengerMiddleName;

    @Column
    private int passengerPassportId;

    @Column
    private int passengerPassportSeries;

    public int getCost() {
        return trips.stream()
                .map(Trip::getCost)
                .reduce(Integer::sum).orElse(0);
    }
}
