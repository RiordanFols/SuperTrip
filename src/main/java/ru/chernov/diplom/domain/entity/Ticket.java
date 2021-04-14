package ru.chernov.diplom.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    // todo: Set sequence of trips
    @OneToMany(fetch = FetchType.EAGER)
    private List<Trip> trips;

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
