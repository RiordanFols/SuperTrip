package ru.chernov.supertrip.utils;


import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Pavel Chernov
 */
public class ScheduleGenerator {

    private int currentTripId;

    private final long from;
    private final long to;
    private final int busCost;
    private final int trainCost;
    private final int planeCost;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private LocalDateTime busStart = LocalDateTime.parse("01-06-2021 06:00", dtf);
    private final long busTripTimeInMinutes;
    private final LocalDateTime busFinish = LocalDateTime.parse("01-06-2021 23:00", dtf);

    private LocalDateTime trainStart = LocalDateTime.parse("01-06-2021 04:00", dtf);
    private final long trainTripTimeInMinutes;
    private final LocalDateTime trainFinish = LocalDateTime.parse("01-06-2021 23:00", dtf);

    private LocalDateTime planeStart = LocalDateTime.parse("01-06-2021 00:00", dtf);
    private final long planeTripTimeInMinutes;
    private final LocalDateTime planeFinish = LocalDateTime.parse("02-06-2021 00:00", dtf);

    private final HashMap<TwoLongKey, Integer> edgesIdsByNodes = new HashMap<>() {{
        put(new TwoLongKey(1, 2), 101);
        put(new TwoLongKey(2, 1), 102);
        put(new TwoLongKey(1, 3), 103);
        put(new TwoLongKey(3, 1), 104);
        put(new TwoLongKey(2, 3), 105);
        put(new TwoLongKey(3, 2), 106);
        put(new TwoLongKey(1, 6), 107);
        put(new TwoLongKey(6, 1), 108);
        put(new TwoLongKey(3, 6), 109);
        put(new TwoLongKey(6, 3), 110);
        put(new TwoLongKey(2, 4), 111);
        put(new TwoLongKey(4, 2), 112);
        put(new TwoLongKey(3, 4), 113);
        put(new TwoLongKey(4, 3), 114);
        put(new TwoLongKey(4, 5), 115);
        put(new TwoLongKey(5, 4), 116);
        put(new TwoLongKey(5, 6), 117);
        put(new TwoLongKey(6, 5), 118);
    }};

    public ScheduleGenerator(int startId, long from, long to, int busCost, int trainCost, int planeCost,
                             long busTripTimeInMinutes, long trainTripTimeInMinutes, long planeTripTimeInMinutes) {
        this.currentTripId = startId;
        this.from = from;
        this.to = to;
        this.busCost = busCost;
        this.trainCost = trainCost;
        this.planeCost = planeCost;
        this.busTripTimeInMinutes = busTripTimeInMinutes;
        this.trainTripTimeInMinutes = trainTripTimeInMinutes;
        this.planeTripTimeInMinutes = planeTripTimeInMinutes;
    }

    public static void main(String[] args) {
        ScheduleGenerator sg;
        sg = new ScheduleGenerator(200, 1, 2, 31, 30, 0, 535, 400, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(500, 2, 1, 31, 30, 0, 535, 400, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(1000, 1, 3, 0, 35, 0, 0, 450, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(1500, 3, 1, 0, 35, 0, 0, 450, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(2000, 2, 3, 45, 32, 0, 600, 450, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(2500, 3, 2, 45, 32, 0, 600, 450, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(3000, 2, 4, 0, 43, 132, 0, 650, 115);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(3500, 4, 2, 0, 43, 132, 0, 650, 115);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(4000, 3, 4, 0, 62, 196, 0, 750, 130);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(4500, 4, 3, 0, 62, 196, 0, 750, 130);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(5000, 1, 6, 0, 31, 0, 0, 550, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(5500, 6, 1, 0, 31, 0, 0, 550, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(6000, 3, 6, 16, 0, 0, 470, 0, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(6500, 6, 3, 16, 0, 0, 470, 0, 0);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(7000, 4, 5, 51, 44, 136, 870, 650, 115);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(7500, 5, 4, 51, 44, 136, 870, 650, 115);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(8000, 5, 6, 0, 41, 174, 0, 600, 105);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
        sg = new ScheduleGenerator(8500, 6, 5, 0, 41, 174, 0, 600, 105);
        sg.printTripsSchedule();
        System.out.println("\n\n\n\n\n");
    }

    public void printTripsSchedule() {
        System.out.println("insert into trip (id, edge_id, from_time, to_time, cost, type) values ");
        if (busTripTimeInMinutes != 0) {
            busStart = generateFirstTripTime(busStart);
            while (busStart.isBefore(busFinish)) {
                var startTime = busStart;
                var finishTime = busStart.plusMinutes(busTripTimeInMinutes);

                for (int i = 0; i < 31; i++) {
                    var edgeId = edgesIdsByNodes.get(new TwoLongKey(from, to));
                    System.out.printf("(%d, %d, '%s', '%s', %d, %s),\n",
                            currentTripId, edgeId,
                            startTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            finishTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            busCost, "'BUS'");
                    currentTripId++;
                }

                long minBreakInMinutesForBus = 200;
                busStart = generateTripTime(startTime, minBreakInMinutesForBus);
            }
        }
        if (trainTripTimeInMinutes != 0) {
            trainStart = generateFirstTripTime(trainStart);
            while (trainStart.isBefore(trainFinish)) {
                var startTime = trainStart;
                var finishTime = trainStart.plusMinutes(trainTripTimeInMinutes);

                for (int i = 0; i < 31; i++) {
                    var edgeId = edgesIdsByNodes.get(new TwoLongKey(from, to));
                    System.out.printf("(%d, %d, '%s', '%s', %d, %s),\n",
                            currentTripId, edgeId,
                            startTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            finishTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            trainCost, "'TRAIN'");
                    currentTripId++;
                }

                long minBreakInMinutesForTrain = 340;
                trainStart = generateTripTime(startTime, minBreakInMinutesForTrain);
            }
        }
        if (planeTripTimeInMinutes != 0) {
            planeStart = generateFirstTripTime(planeStart);
            while (planeStart.isBefore(planeFinish)) {
                var startTime = planeStart;
                var finishTime = planeStart.plusMinutes(planeTripTimeInMinutes);

                for (int i = 0; i < 31; i++) {
                    var edgeId = edgesIdsByNodes.get(new TwoLongKey(from, to));
                    System.out.printf("(%d, %d, '%s', '%s', %d, %s),\n",
                            currentTripId, edgeId,
                            startTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            finishTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            planeCost, "'PLANE'");
                    currentTripId++;
                }

                long minBreakInMinutesForPlane = 460;
                planeStart = generateTripTime(startTime, minBreakInMinutesForPlane);
            }
        }
    }

    private static LocalDateTime generateFirstTripTime(LocalDateTime startTime) {
        Random random = new Random();
        var hours = random.nextInt(4);
        var minutes = random.nextInt(13) * 5;
        startTime = startTime.plusHours(hours);
        startTime = startTime.plusMinutes(minutes);
        return startTime;
    }

    private static LocalDateTime generateTripTime(LocalDateTime lastTripTime, long minBreakInMinutes) {
        Random random = new Random();
        var minutes = random.nextInt(13) * 5;
        return lastTripTime
                .plusMinutes(minBreakInMinutes)
                .plusMinutes(minutes);
    }

    @EqualsAndHashCode
    private static class TwoLongKey {
        private final long int1;
        private final long int2;

        public TwoLongKey(long int1, long int2) {
            this.int1 = int1;
            this.int2 = int2;
        }
    }
}
