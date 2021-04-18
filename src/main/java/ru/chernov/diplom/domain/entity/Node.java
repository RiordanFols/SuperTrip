package ru.chernov.diplom.domain.entity;

import lombok.Data;
import ru.chernov.diplom.domain.TransportType;

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

    // todo: open-closed P
    @OneToOne(fetch = FetchType.EAGER)
    private TransferMap transferMap;

    public long getTransferTime(TransportType type1, TransportType type2) {
        switch (type1) {
            case BUS -> {
                return switch (type2) {
                    case BUS -> transferMap.getBB();
                    case TRAIN -> transferMap.getBT();
                    case PLANE -> transferMap.getBP();
                };
            }
            case TRAIN -> {
                return switch (type2) {
                    case BUS -> transferMap.getBT();
                    case TRAIN -> transferMap.getTT();
                    case PLANE -> transferMap.getTP();
                };
            }
            case PLANE -> {
                return switch (type2) {
                    case BUS -> transferMap.getBP();
                    case TRAIN -> transferMap.getTP();
                    case PLANE -> transferMap.getPP();
                };
            }
        }
        return 0;
    }
}
