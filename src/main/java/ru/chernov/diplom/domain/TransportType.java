package ru.chernov.diplom.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
public enum TransportType {
    BUS,
    TRAIN,
    PLANE;

    public static Set<TransportType> getAll() {
        return Arrays.stream(TransportType.values())
                .collect(Collectors.toSet());
    }
}
