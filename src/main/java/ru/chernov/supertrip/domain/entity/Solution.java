package ru.chernov.supertrip.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.chernov.supertrip.alg.solver.SolutionType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Chernov
 */
@Data
@Entity
@EqualsAndHashCode(of = {"time", "cost"})
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm")
    private LocalDateTime creationDateTime;

    @Column(nullable = false, updatable = false)
    private long time;

    @Column(nullable = false, updatable = false)
    private double cost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "solution_trips",
            joinColumns = @JoinColumn(name = "solution_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "trip_id", nullable = false, updatable = false))
    private List<Trip> trips = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private SolutionType type;

    public Trip getLastTrip() {
        return trips.isEmpty() ? null : trips.get(trips.size() - 1);
    }
}
