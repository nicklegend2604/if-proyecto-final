package com.nicolas.proyectoif.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtil {

    public static LocalDateTime getRandomDateTime(LocalDate startDate, LocalDate endDate) {
        long randomEpochDay = ThreadLocalRandom.current().longs(startDate.toEpochDay(), endDate.toEpochDay()).findAny().getAsLong();
        LocalDate randomLocalDate = LocalDate.ofEpochDay(randomEpochDay);

        LocalTime time1 = LocalTime.of(0, 0, 0);
        LocalTime time2 = LocalTime.of(23, 59, 59);
        int secondOfDayTime1 = time1.toSecondOfDay();
        int secondOfDayTime2 = time2.toSecondOfDay();
        Random random = new Random();
        int randomSecondOfDay = secondOfDayTime1 + random.nextInt(secondOfDayTime2-secondOfDayTime1);
        LocalTime randomLocalTime = LocalTime.ofSecondOfDay(randomSecondOfDay);

        return LocalDateTime.of(randomLocalDate, randomLocalTime);
    }
}
