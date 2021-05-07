package ru.chernov.diplom.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Getter
public enum TransportType {
    BUS(80),
    TRAIN(140),
    PLANE(800);

    private final int speed;

    TransportType(int speed) {
        this.speed = speed;
    }

    public static Set<TransportType> getAll() {
        return Arrays.stream(TransportType.values())
                .collect(Collectors.toSet());
    }

    public static int getFastestTransportSpeed(Collection<TransportType> allowedTypes) {
        return allowedTypes.stream()
                .max(Comparator.comparingLong(TransportType::getSpeed))
                .map(TransportType::getSpeed).orElse(0);
    }
}
