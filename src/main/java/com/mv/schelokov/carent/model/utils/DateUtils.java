package com.mv.schelokov.carent.model.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class DateUtils {
    
    /**
     * 
     * @param startDate
     * @param endDate
     * @return number of days between two dates
     */
    public static int days(Date startDate, Date endDate) {
        return (int) TimeUnit.MILLISECONDS.toDays(endDate.getTime()
                - startDate.getTime());
    }
    
    /**
     * 
     * @param date
     * @return date with hours, minutes, seconds and milliseconds is set to 0
     */
    public static Date onlyDate(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
        cal.set(GregorianCalendar.MINUTE, 0);
        cal.set(GregorianCalendar.SECOND, 0);
        cal.set(GregorianCalendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
