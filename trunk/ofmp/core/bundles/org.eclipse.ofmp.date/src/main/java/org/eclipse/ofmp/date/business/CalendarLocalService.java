package org.eclipse.ofmp.date.business;

import java.util.Date;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.currency.dom.Currency;

public interface CalendarLocalService extends CalendarService
{
    public void addHoliday(Currency currency, Date aHolidayDate) throws OFMPException;

    public Date rewindToTheEndOfTheBusinessDay(Date aTradeDateTo);
}
