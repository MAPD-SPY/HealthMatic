package com.spy.healthmatic.Doctor.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-20.
 */

public class TimeHelpers {

    public static final String FORMAT_YYYMMDD_HMM_A = "yyyy-MM-dd, h:mm a";
    public static final String FORMAT_YYYMMDD = "yyyy-MM-dd";

    /**
     * Get the latest date and time stamp
     * @return Returns the date and/or time depending on the selected format
     */
    public static String getCurrentDateAndTime(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeDateFormat = new SimpleDateFormat(format);
        return timeDateFormat.format(calendar.getTime());
    }
}
