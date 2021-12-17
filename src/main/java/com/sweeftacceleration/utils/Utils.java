package com.sweeftacceleration.utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {
    public static Date addMinute(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, i);
        return cal.getTime();
    }

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, i);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    public static String getRandomNumber() {
        Random random = new Random();

        return String.format("%04d", random.nextInt(10000));
    }

    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }

}
