package ru.chernov.supertrip.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
