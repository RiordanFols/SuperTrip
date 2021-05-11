package ru.chernov.diplom.alg.solver;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum SolutionType {
    COST_ABSOLUTE(0, 0),
    COST_PRIORITY(0, 33.33),
    OPTIMAL(33.34, 66.66),
    TIME_PRIORITY(66.67, 100),
    TIME_ABSOLUTE(100, 100);

    private final double priceLimitMin;
    private final double priceLimitMax;

    SolutionType(double priceCategoryMin, double priceCategoryMax) {
        this.priceLimitMin = priceCategoryMin;
        this.priceLimitMax = priceCategoryMax;
    }
}
