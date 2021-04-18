package ru.chernov.diplom.component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author Pavel Chernov
 */
public class ScheduleGenerator {

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

    private int id;

    public ScheduleGenerator(int startId, long from, long to, int busCost, int trainCost, int planeCost,
                             long busTripTimeInMinutes, long trainTripTimeInMinutes, long planeTripTimeInMinutes) {
        this.id = startId;
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
        ScheduleGenerator sg = new ScheduleGenerator(1, 1, 2, 31, 30, 0, 535, 400, 0);
        sg.go();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(500, 2, 1, 31, 30, 0, 535, 400, 0);
        sg.go();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(1000, 1, 3, 0, 35, 0, 0, 450, 0);
        sg.go();
        System.out.println("\n\n\n\n\n");

        sg = new ScheduleGenerator(1500, 3, 1, 0, 35, 0, 0, 450, 0);
        sg.go();
        System.out.println("\n\n\n\n\n");
    }
    public void go() {
        System.out.println("insert into trip (id, from_id, to_id, from_time, to_time, cost, type) values ");
        if (busTripTimeInMinutes != 0) {
            busStart = generateFirstTripTime(busStart);
            while (busStart.isBefore(busFinish)) {
                var startTime = busStart;
                var finishTime = busStart.plusMinutes(busTripTimeInMinutes);

                for (int i = 0; i < 31; i++) {
                    System.out.printf("(%d, %d, %d, '%s', '%s', %d, %s),\n",
                            id, from, to,
                            startTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            finishTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            busCost, "'bus'");
                    id++;
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
                    System.out.printf("(%d, %d, %d, '%s', '%s', %d, %s),\n",
                            id, from, to,
                            startTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            finishTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            trainCost, "'train'");
                    id++;
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
                    System.out.printf("(%d, %d, %d, '%s', '%s', %d, %s),\n",
                            id, from, to,
                            startTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            finishTime.plusDays(i).toString().replaceAll("T", " ") + ":00+03",
                            planeCost, "'plane'");
                    id++;
                }

                long minBreakInMinutesForPlane = 460;
                planeStart = generateTripTime(startTime, minBreakInMinutesForPlane);
            }
        }
    }

    private static LocalDateTime generateFirstTripTime(LocalDateTime startTime) {
        Random random = new Random();
        var hours = random.nextInt(4);
        var minutes = random.nextInt(12) * 5;
        startTime = startTime.plusHours(hours);
        startTime = startTime.plusMinutes(minutes);
        return startTime;
    }

    private static LocalDateTime generateTripTime(LocalDateTime lastTripTime, long minBreakInMinutes) {
        Random random = new Random();
        var minutes = random.nextInt(12) * 5;
        return lastTripTime
                .plusMinutes(minBreakInMinutes)
                .plusMinutes(minutes);
    }
}
