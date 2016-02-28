package Calculations;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class CalculateDate {


    private static void getChronoUnitForSecondAfterFirst(LocalDateTime firstLocalDateTime, LocalDateTime secondLocalDateTime, long[] chronoUnits) {
    /*Separate LocaldateTime on LocalDate and LocalTime*/
        LocalDate firstLocalDate = firstLocalDateTime.toLocalDate();
        LocalTime firstLocalTime = firstLocalDateTime.toLocalTime();

        LocalDate secondLocalDate = secondLocalDateTime.toLocalDate();
        LocalTime secondLocalTime = secondLocalDateTime.toLocalTime();

    /*Calculate the time difference*/
        Duration duration = Duration.between(firstLocalDateTime, secondLocalDateTime);
        long durationDays = duration.toDays();
        Duration throughoutTheDayDuration = duration.minusDays(durationDays);
        Period period = Period.between(firstLocalDate, secondLocalDate);

    /*Correct the date difference*/
        if (secondLocalTime.isBefore(firstLocalTime)) {
            period = period.minusDays(1);

        }

        /*Calculate chrono unit values and  write it in array*/
        chronoUnits[0] = period.getYears();
        chronoUnits[1] = period.getMonths();
        chronoUnits[2] = period.getDays();
        chronoUnits[3] = throughoutTheDayDuration.toHours();
        chronoUnits[4] = throughoutTheDayDuration.toMinutes() % 60;
        chronoUnits[5] = throughoutTheDayDuration.getSeconds() % 60;
    }

    public static long[] getChronoUnits(String firstLocalDateTimeString, String secondLocalDateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime firstLocalDateTime = LocalDateTime.parse(firstLocalDateTimeString, formatter);
        LocalDateTime secondLocalDateTime = LocalDateTime.parse(secondLocalDateTimeString, formatter);

        long[] chronoUnits = new long[6];
        if (secondLocalDateTime.isAfter(firstLocalDateTime)) {
            getChronoUnitForSecondAfterFirst(firstLocalDateTime, secondLocalDateTime, chronoUnits);
        } //else {
            //getChronoUnitForSecondAfterFirst(secondLocalDateTime, firstLocalDateTime, chronoUnits);
       // }
        return chronoUnits;
    }



}
