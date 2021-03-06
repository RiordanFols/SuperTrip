package ru.chernov.supertrip.alg.solver;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum SolutionType {
    COST(0),
    TIME(0),
    TIME_OPTIMAL(0.95),
    COST_OPTIMAL(0.95);

    private final double threshold;

    SolutionType(double threshold) {
        this.threshold = threshold;
    }
}
