package org.eclipse.ofmp.date.business;

import java.util.Date;

public abstract class DateListenerAdapter implements DateListener
{
    public void afterBusinessDayBegin(Date aDay)
    {
    }

    public void afterBusinessDayEnd(Date aDay)
    {
    }

    public void beforeBusinessDayBegin(Date aDay)
    {
    }

    public void beforeBusinessDayEnd(Date aDay)
    {
    }
}
