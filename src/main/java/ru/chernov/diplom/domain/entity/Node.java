package ru.chernov.diplom.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, updatable = false, length = 50)
    private String name;

    @Column(nullable = false, updatable = false)
    private float x;

    @Column(nullable = false, updatable = false)
    private float y;

    // todo: transferMap
}
