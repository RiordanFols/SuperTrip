package ru.chernov.diplom.page.error;

import lombok.AllArgsConstructor;

/**
 * @author Pavel Chernov
 */
@AllArgsConstructor
public enum MainError {

    WRONG_NODE_NAME("Can't find city "),
    SAME_NODES("Departure and arrival cities are the same"),
    NEGATIVE_TIME_DIF("Arrival time can't be earlier than departure time"),
    NO_TRANSPORT_SELECTED("You need to choose at least 1 transport");

    private final String description;

    @Override
    public String toString() {
        return this.description;
    }
}