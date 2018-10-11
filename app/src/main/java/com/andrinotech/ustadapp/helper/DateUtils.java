package com.andrinotech.ustadapp.helper;



import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AhadAs on 7/24/2017.
 */

public class DateUtils {
    public static String DateFormat = "dd/MM/yyyy";
    public static String DateTimeFormat = "dd-MM-yy hh:mm";
    public static String TimeFormat = "hh:mm";
    private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(DateTimeFormat);
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat);
    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(TimeFormat);

    public static String ConvertMilliSecondsToDate(Long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String ConvertMilliSecondsToDateTime(Long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return simpleDateTimeFormat.format(calendar.getTime());
    }

    public static String ConvertMilliSecondsToTime(Long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return simpleTimeFormat.format(calendar.getTime());
    }


    public static String convertMillisecondsToTime(Long milliseconds) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(milliseconds);
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "";
        int hours = cl.get(Calendar.HOUR_OF_DAY);
        String ampm = hours < 12 ? " AM" : " PM";
        int hrs = hours <= 12 ? hours :  hours - 12;
        time = "" + Utils.getTwoDecimalplaces(hrs) + ":" + Utils.getTwoDecimalplaces(cl.get(Calendar.MINUTE)) + ampm;
        return time;
    }

    public static long getDifferenceInMinutes(Long timeInMillisecond) {
        long millis = Calendar.getInstance().getTimeInMillis() - timeInMillisecond;
        long Hours = millis / (1000 * 60 * 60);
        long mins = (millis / 1000) / 60;
        return mins;
    }

    public static String getDifferenceInMinute(Long timeInMillisecond) {
        long millis = Calendar.getInstance().getTimeInMillis() - timeInMillisecond;
        long Hours = millis / (1000 * 60 * 60);
        long mins = (millis / 1000) / 60;
        String min = String.valueOf(mins);
        return min;
    }
    public static long getMillisecondsForHistory() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        return calendar.getTimeInMillis();
    }
}
