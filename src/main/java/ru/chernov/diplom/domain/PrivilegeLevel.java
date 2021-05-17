package ru.chernov.diplom.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Pavel Chernov
 */
@Getter
public enum PrivilegeLevel {
    BRONZE(0, 1, 0.0),
    SILVER(1, 1.5, 1000.0),
    GOLDEN(2, 2, 5000.0),
    PLATINUM(3, 2.5, 20000.0);

    private final int id;
    private final double discount;
    private final double threshold;

    PrivilegeLevel(int id, double discount, double threshold) {
        this.id = id;
        this.discount = discount;
        this.threshold = threshold;
    }

    public static PrivilegeLevel getLevel(double spent) {
        var reverseLevels = Arrays.asList(PrivilegeLevel.values());
        Collections.reverse(reverseLevels);

        for (var level : reverseLevels) {
            if (spent >= level.getThreshold())
                return level;
        }

        return null;
    }

    public PrivilegeLevel getNextLevel() {
        return Arrays.stream(PrivilegeLevel.values())
                .filter(e -> e.getId() == this.getId() + 1)
                .findFirst().orElse(null);
    }
}
