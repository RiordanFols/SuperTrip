package ru.chernov.supertrip.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Edge {

    // todo: key of from and to
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Node from;

    @ManyToOne(fetch = FetchType.EAGER)
    private Node to;
}