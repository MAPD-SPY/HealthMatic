package com.spy.healthmatic.Doctor.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-20.
 */

public class TimeHelpers {

    public static String getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd, h:mm a");
        return timeDateFormat.format(calendar.getTime());
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return timeDateFormat.format(calendar.getTime());
    }
}
