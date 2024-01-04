package com.xm.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String currentDate() {
        return new SimpleDateFormat("yyyy MMM dd", Locale.ENGLISH).format(new Date());
    }

    public static String nextDate() {
        return new SimpleDateFormat("yyyy MMM dd", Locale.ENGLISH).format(new Date(new Date().getTime() + 86400000));
    }

    public static String nextMonday() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd", Locale.ENGLISH);
        return nextMonday.format(formatter);
    }
}
