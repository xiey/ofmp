package org.eclipse.ofmp.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class OFMPDateUtils
{
    /**
     *
     */
    private OFMPDateUtils()
    {
    }

    /**
     * Returns the difference in milliseconds between date1 and date2. <br>
     * If the return value is negative, date1 is before date2.
     * 
     * @param date1
     * @param date2
     * @return long the number of milliseconds between date1 and date2
     */
    public static long diffMillis(Date date1, Date date2)
    {
        Calendar cal1 = new OFMPCalendar(date1);
        Calendar cal2 = new OFMPCalendar(date2);
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.getTimeInMillis() - cal2.getTimeInMillis();
    }

    /**
     * Returns the difference in days between date1 and date2. If the return value is negative, date1 is before date2.
     * 
     * @param date1
     * @param date2
     * @return long the number of days between the given date and the current date/time.
     */
    public static long diffDays(Date date1, Date date2)
    {
        return diffMillis(date1, date2) / (24 * 60 * 60 * 1000);
    }

    public static Date addDays(Date aDate, int aNumberOfDays)
    {
        Calendar cal = new OFMPCalendar(aDate);
        cal.add(Calendar.DAY_OF_MONTH, aNumberOfDays);
        return cal.getTime();
    }

    public static Date addMonths(Date date, int numberOfMonths)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, numberOfMonths);
        return cal.getTime();
    }

    /**
     * Transforms a Date object to a String using the given format
     * 
     * @param date
     * @param format
     * @return String
     */
    public static String dateToString(Date date, String format)
    {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * Attempts to convert a String to a Date, using the given format
     * 
     * @param date
     * @param format
     * @return Date, null if ParseException occurs
     */
    public static Date toDate(String date, String format)
    {
        DateFormat formatter = new SimpleDateFormat(format);
        try
        {
            return formatter.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * Creates a Date object from the given parms. Time will be 00:00:00.
     * 
     * @param year
     * @param month
     * @param day
     * @return Date
     */
    public static Date getDate(int year, int month, int day)
    {
        assert (month > 0) : "the month parameter is non-zero based";

        return getDate(year, month, day, 0, 0, 0);
    }

    /**
     * Creates a Date object from the given parms.
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return Date
     */
    public static Date getDate(int year, int month, int day, int hour, int minute, int second)
    {
        assert (month > 0) : "the month parameter is non-zero based";

        Calendar cal = new GregorianCalendar(year, month - 1, day, hour, minute, second);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * Returns the date, resetting hours minutes seconds and ms to zero
     */
    public static Date getDate(Date date)
    {
        Calendar cal = new OFMPCalendar(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Check if the date is a weekend day.
     * 
     * @param date
     * @return true if the date is a saturday or a sunday
     * @return false if the date is from monday to friday included
     */
    public static boolean isWeekEndDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    /**
     * Compare 2 dates about their YYYYMMDD format, removing hours minutes and seconds in the comparison.
     * 
     * @return -1 if date1 is lower than date2.
     * @return 0 if date1 equals date2.
     * @return 1 if date1 is greater than date2.
     */
    public static int compare(Date date1, Date date2)
    {
        return org.apache.commons.lang.time.DateUtils.truncate(date1, Calendar.DATE).compareTo(
                org.apache.commons.lang.time.DateUtils.truncate(date2, Calendar.DATE));
    }

    public static Date getMax(Date date1, Date date2)
    {
        if (compare(date1, date2) == 1)
            return date1;
        else
            return date2;
    }

    public static Date getLower(Collection<Date> aDateCollection)
    {
        assert aDateCollection != null;
        assert aDateCollection.size() != 0;

        Date result = null;
        for (Date date : aDateCollection)
            if (result == null || OFMPDateUtils.compare(date, result) == -1)
                result = date;

        return result;
    }

    public static Date getHigher(Collection<Date> aDateCollection)
    {
        assert aDateCollection != null;
        assert aDateCollection.size() != 0;

        Date result = null;
        for (Date date : aDateCollection)
            if (result == null || OFMPDateUtils.compare(date, result) == 1)
                result = date;

        return result;
    }
}
