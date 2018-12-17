package todday.funny.seoulcatcher.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import todday.funny.seoulcatcher.R;

public class DateFormat {
    private final static String DATE_FORMAT = "yyyy-MM-dd (E)";
    private final static String DATE_FORMAT_NONDAY = "yyyy-MM-dd";

    private final static String TIME_FORMAT = "a HH:mm";
    private final static String TIMER_FORMAT = "mm:ss";

    public static String getStringFromDate(Context context, long date) {
        long diffTime = (System.currentTimeMillis() - date) / 1000;
        String text = "";
        if (diffTime < 60) {
            text = context.getString(R.string.just_before);
        } else if ((diffTime /= 60) < 60) {
            text = context.getString(R.string.minutes_ago, String.valueOf(diffTime));
        } else if ((diffTime /= 60) < 24) {
            text = context.getString(R.string.hours_ago, String.valueOf(diffTime));
        } else if ((diffTime /= 24) < 3) {
            text = context.getString(R.string.days_ago, String.valueOf(diffTime));
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            try {
                Date d = formatter.parse(String.valueOf(date));
                formatter.format(d);
                text = formatter.format(d);
                return formatter.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    public static String getDdayStringFromCalendar(Context context, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            dateFormat.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateFormat.parse(date));

            Calendar current = Calendar.getInstance();
            GregorianCalendar curreuntCalendar = new GregorianCalendar(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE), 0, 0, 0);

            int dday = (int) ((curreuntCalendar.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / 3600 / 24);

            if (dday == 0) {
                return "D-Day";
            } else if (dday > 0) {
                return dday + context.getString(R.string.days_after);
            }
            return Math.abs(dday) + context.getString(R.string.days_remaining);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    public static Calendar getCalendarString(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        try {
            formatter.parse(date);
            calendar.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static String getStringCalendar(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(calendar.getTime());
    }


    public static String getCallTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        return dateFormat.format(calendar.getTime());
    }

    public static String getTimer(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIMER_FORMAT);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        return dateFormat.format(calendar.getTime());
    }
}
