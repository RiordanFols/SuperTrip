package ru.chernov.diplom.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.chernov.diplom.domain.TransportType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Node from;

    @ManyToOne(fetch = FetchType.EAGER)
    private Node to;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime fromTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime toTime;

    @Column(nullable = false)
    private int cost;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TransportType type;

    public long getTravelTime() {
        return ChronoUnit.MINUTES.between(fromTime, toTime);
    }
}
