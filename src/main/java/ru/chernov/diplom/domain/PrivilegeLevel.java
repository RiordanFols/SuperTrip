package ru.chernov.diplom.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Pavel Chernov
 */
@Getter
public enum PrivilegeLevel {
    BRONZE(1, 0),
    SILVER(1.5, 1000),
    GOLD(2, 5000),
    PLATINUM(2.5, 20000);

    private final double discount;
    private final int threshold;

    PrivilegeLevel(double discount, int threshold) {
        this.discount = discount;
        this.threshold = threshold;
    }

    public static PrivilegeLevel getLevel(int spent) {
        var reverseLevels = Arrays.asList(PrivilegeLevel.values());
        Collections.reverse(reverseLevels);

        for (var level : reverseLevels) {
            if (spent >= level.getThreshold())
                return level;
        }

        return null;
    }
}
