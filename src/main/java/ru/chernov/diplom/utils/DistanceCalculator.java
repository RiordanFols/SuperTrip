package ru.chernov.diplom.utils;

import ru.chernov.diplom.domain.entity.Node;

/**
 * @author Pavel Chernov
 */
// magic of math
public class DistanceCalculator {

    // radius of the Earth
    public static final int R = 6373;

    public static int calculate(Node node1, Node node2) {
        var lat1 = Math.toRadians(node1.getLat());
        var lon1 = Math.toRadians(node1.getLon());
        var lat2 = Math.toRadians(node2.getLat());
        var lon2 = Math.toRadians(node2.getLon());

        var lat1Sin = Math.sin(lat1);
        var lat2Sin = Math.sin(lat2);
        var lat1Cos = Math.cos(lat1);
        var lat2Cos = Math.cos(lat2);
        var sinDelLon = Math.sin(lon2 - lon1);
        var cosDelLon = Math.cos(lon2 - lon1);

        var y = Math.sqrt(Math.pow(lat2Cos * sinDelLon, 2) +
                Math.pow(lat1Cos * lat2Sin - lat1Sin * lat2Cos * cosDelLon, 2));
        var x = lat1Sin * lat2Sin + lat1Cos * lat2Cos * cosDelLon;

        return (int) (Math.atan2(y, x) * R);
    }
}
