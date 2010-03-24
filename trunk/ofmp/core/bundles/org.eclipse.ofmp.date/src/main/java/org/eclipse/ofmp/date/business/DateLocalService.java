package org.eclipse.ofmp.date.business;

import java.util.Date;

import org.eclipse.ofmp.common.exception.OFMPException;

public interface DateLocalService extends DateService
{
    public void addDateListener(DateListener aListener);

    public void removeDateListener(DateListener aListener);

    public void setCurrent(Date aDate);

    public void setCurrent(Date aDate, Boolean aFireListeners);

    public int getDayStart();

    public int getDayEnd();

    public Date addWorkingDays(int aNumberOfDays) throws OFMPException;

    public Date addWorkingDays(Date aDate, int aNumberOfDays) throws OFMPException;

    public void setBusinessDateOverride(Date aBusinessDateOverride) throws OFMPException;

}
