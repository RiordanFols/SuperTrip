package ru.chernov.diplom.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pavel Chernov
 */
@Entity
@Data
public class TransferMap {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private long BB;
    private long TT;
    private long PP;
    private long BT;
    private long BP;
    private long TP;
}
