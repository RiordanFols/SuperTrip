package ru.chernov.supertrip.page.error;

import lombok.AllArgsConstructor;

/**
 * @author Pavel Chernov
 */
@AllArgsConstructor
public enum MainError {

    WRONG_NODE_NAME("Cannot find city "),
    SAME_NODES("Departure and arrival cities cannot be the same"),
    NEGATIVE_TIME_DIFF("Arrival time cannot be earlier than departure time"),
    NO_TRANSPORT_SELECTED("You need to choose at least 1 transport"),
    NO_TRIPS_AVAILABLE("Sorry, cannot find any trips for you");

    private final String description;

    @Override
    public String toString() {
        return this.description;
    }
}