package ru.chernov.diplom.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Trip> trips = new TreeSet<>(Comparator.comparing(Trip::getFromTime));

    @Column(length = 25, nullable = false, updatable = false)
    private String passengerName;

    @Column(length = 25, nullable = false, updatable = false)
    private String passengerSurname;

    @Column(length = 25, nullable = false, updatable = false)
    private String passengerMiddleName;

    @Column(length = 6, nullable = false, updatable = false)
    private String passengerPassportId;

    @Column(length = 4, nullable = false, updatable = false)
    private String passengerPassportSeries;
}
