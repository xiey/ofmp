package org.eclipse.ofmp.date.business;

import java.util.Collection;
import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.date.dom.CalendarEvent;

public interface CalendarService
{
    public DateCalculator<Date> getDateCalculator(Currency aCurrency) throws OFMPException;

    public DateCalculator<Date> getReverseDateCalculator(Currency aCurrency) throws OFMPException;

    public DateCalculator<Date> getDateCalculator() throws OFMPException;

    public DateCalculator<Date> getReverseDateCalculator() throws OFMPException;

    public Collection<CalendarEvent> getEvents(Currency aCurrency);

    public boolean isHolidayForCurrencies(Date aDate, Currency aCurrency1, Currency aCurrency2) throws OFMPException;
}
