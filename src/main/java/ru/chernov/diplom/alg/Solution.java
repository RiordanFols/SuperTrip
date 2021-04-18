package ru.chernov.diplom.alg;

import lombok.Data;
import ru.chernov.diplom.domain.entity.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Chernov
 */
@Data
public class Solution {
    private long time;
    private long cost;
    private List<Trip> trips = new ArrayList<>();

    public Trip getLastTrip() {
        return trips.get(trips.size() - 1);
    }
}
