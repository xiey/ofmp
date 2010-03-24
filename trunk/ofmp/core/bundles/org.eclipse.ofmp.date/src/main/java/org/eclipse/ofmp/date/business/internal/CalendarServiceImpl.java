package org.eclipse.ofmp.date.business.internal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.jdk.DateKitCalculatorsFactory;

import org.apache.log4j.Logger;
import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.currency.business.CurrencyService;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.date.business.CalendarLocalService;
import org.eclipse.ofmp.date.dom.CalendarEvent;
import org.springframework.beans.factory.InitializingBean;

public class CalendarServiceImpl implements InitializingBean, CalendarLocalService
{
    private static DateFormat s_DateFormat = new SimpleDateFormat("yyyyMMdd");

    private final Logger log = Logger.getLogger(getClass());

    private String m_CalendarFile;

    private final List<CalendarEvent> m_Events = new ArrayList<CalendarEvent>();

    private final Map<Currency, List<CalendarEvent>> m_EventsMap = new HashMap<Currency, List<CalendarEvent>>();

    private CurrencyService m_CurrencyService;

    private DateKitCalculatorsFactory m_DateCalculatorsFactory;

    public void afterPropertiesSet() throws Exception
    {
        InputStream is = CalendarServiceImpl.class.getResourceAsStream(m_CalendarFile);

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            HashSet<String> badCurrencies = new HashSet<String>();

            String line = null;
            int n = 0;
            while ((line = reader.readLine()) != null)
            {
                if (n > 0)
                {
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");

                    String currencyCode = tokenizer.nextToken();

                    if (badCurrencies.contains(currencyCode))
                        continue;

                    Currency currency = m_CurrencyService.findCurrency(currencyCode);
                    if (currency != null)
                    {
                        CalendarEvent event = new CalendarEvent();

                        event.setCurrency(currency);
                        event.setCountry(tokenizer.nextToken());
                        event.setFinancialCenter(tokenizer.nextToken());

                        tokenizer.nextToken(); // year

                        event.setDate(s_DateFormat.parse(tokenizer.nextToken()));

                        tokenizer.nextToken(); // day of the week

                        event.setName(tokenizer.nextToken());

                        m_Events.add(event);

                        List<CalendarEvent> events = m_EventsMap.get(currency);
                        if (events == null)
                        {
                            events = new ArrayList<CalendarEvent>();
                            m_EventsMap.put(currency, events);
                        }

                        events.add(event);
                    }
                    else
                    {
                        log.warn("Unknown currency: " + currencyCode);
                        badCurrencies.add(currencyCode);
                    }
                }

                n++;
            }

            buildCalendars();
        }
        finally
        {
            if (is != null)
                is.close();
        }
    }

    private void buildCalendars()
    {
        // Map of Currency to set of holiday dates
        Map<String, HashSet<Date>> holidays = new HashMap<String, HashSet<Date>>();

        for (CalendarEvent event : m_Events)
        {
            HashSet<Date> forCurrency = holidays.get(event.getCurrency().getISOCode());
            if (forCurrency == null)
            {
                forCurrency = new HashSet<Date>();
                holidays.put(event.getCurrency().getISOCode(), forCurrency);
            }

            forCurrency.add(event.getDate());
        }

        m_DateCalculatorsFactory = new DateKitCalculatorsFactory();

        for (String currency : holidays.keySet())
            m_DateCalculatorsFactory.registerHolidays(currency, holidays.get(currency));
    }

    public DateCalculator<Date> getDateCalculator() throws OFMPException
    {
        return m_DateCalculatorsFactory.getDateCalculator("default", HolidayHandlerType.FORWARD);
    }

    public DateCalculator<Date> getDateCalculator(Currency aCurrency) throws OFMPException
    {
        return m_DateCalculatorsFactory.getDateCalculator(aCurrency.getISOCode(), HolidayHandlerType.FORWARD);
    }

    public DateCalculator<Date> getReverseDateCalculator(Currency aCurrency) throws OFMPException
    {
        return m_DateCalculatorsFactory.getDateCalculator(aCurrency.getISOCode(), HolidayHandlerType.BACKWARD);
    }

    public DateCalculator<Date> getReverseDateCalculator() throws OFMPException
    {
        return m_DateCalculatorsFactory.getDateCalculator("default", HolidayHandlerType.BACKWARD);
    }

    public Date rewindToTheEndOfTheBusinessDay(Date aDate)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(aDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public Collection<CalendarEvent> getEvents(Currency aCurrency)
    {
        if (m_EventsMap.containsKey(aCurrency))
            return m_EventsMap.get(aCurrency);
        else
            return new LinkedList<CalendarEvent>();
    }

    public void setCalendarFile(String aCalendarFile)
    {
        m_CalendarFile = aCalendarFile;
    }

    public void setCurrencyService(CurrencyService aCurrencyService)
    {
        m_CurrencyService = aCurrencyService;
    }

    public void addHoliday(Currency aCurrency, Date aHolidayDate) throws OFMPException
    {
        Currency currency = m_CurrencyService.findCurrency(aCurrency.getISOCode());
        if (currency != null)
        {
            CalendarEvent event = new CalendarEvent();

            event.setCurrency(currency);
            event.setDate(aHolidayDate);

            m_Events.add(event);

            List<CalendarEvent> events = m_EventsMap.get(currency);
            if (events == null)
            {
                events = new ArrayList<CalendarEvent>();
                m_EventsMap.put(currency, events);
            }

            events.add(event);
        }
        else
        {
            log.warn("Unknown currency: " + aCurrency.getISOCode());
        }

        buildCalendars();
    }

    public boolean isHolidayForCurrencies(Date aDate, Currency aCurrency1, Currency aCurrency2) throws OFMPException
    {
        DateCalculator<Date> dateCalculator1 = getDateCalculator(aCurrency1);
        DateCalculator<Date> dateCalculator2 = getDateCalculator(aCurrency1);

        if (dateCalculator1 != null && dateCalculator1.isNonWorkingDay(aDate))
            return true;

        if (dateCalculator2 != null && dateCalculator2.isNonWorkingDay(aDate))
            return true;

        return false;
    }
}
