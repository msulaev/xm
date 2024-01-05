package com.xm.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private final static String DATE_FORMAT = "yyyy MMM dd";

    public static String currentDate() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(new Date());
    }

    public static String nextDate() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(new Date(new Date().getTime() + 86400000));
    }

    public static String nextMonday() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH);
        return nextMonday.format(formatter);
    }

    public static LocalDate parseReleaseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
