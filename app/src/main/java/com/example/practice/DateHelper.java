package com.example.practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    public static boolean isDateCorrect(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        format.setLenient(false);

        try {
            Date date = format.parse(value.trim());
            return value.trim().equals(format.format(date));
        } catch (ParseException e) {
            return false;
        }
    }

    public static Calendar getCalendarFromText(String value) {
        Calendar calendar = Calendar.getInstance();

        if (isDateCorrect(value)) {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
            format.setLenient(false);
            try {
                Date date = format.parse(value.trim());
                calendar.setTime(date);
            } catch (ParseException ignored) {
            }
        }

        return calendar;
    }

    public static String makeDateText(int year, int month, int day) {
        return String.format(Locale.getDefault(), "%02d.%02d.%04d", day, month + 1, year);
    }
}
