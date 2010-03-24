package org.eclipse.ofmp.date.business.internal;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;

import org.apache.log4j.Logger;
import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.common.utils.OFMPCalendar;
import org.eclipse.ofmp.date.business.CalendarService;
import org.eclipse.ofmp.date.business.DateLocalService;
import org.eclipse.ofmp.date.business.TimeFlowSimulationBeanMBean;

public class TimeFlowSimulationBean implements TimeFlowSimulationBeanMBean
{
    private final Logger log = Logger.getLogger(getClass());

    private DateLocalService m_DateService;
    private CalendarService m_CalendarService;

    private Date m_Current;

    private boolean m_Enabled;

    public enum RollByUnit
    {
        HOUR, DAY
    }

    public enum BusinessDayBoundary
    {
        START, END
    }

    public void initialize()
    {
        assertSimulationState();

        m_Current = new Date();
        m_DateService.setCurrent(m_Current);
    }

    public void initialize(Date aDate, Boolean aFireListeners)
    {
        assertSimulationState();

        if (aDate == null)
        {
            initialize();
            return;
        }

        m_Current = aDate;
        log.info("Set current date to " + m_Current);
        m_DateService.setCurrent(m_Current, aFireListeners);
    }

    public void rollByHour() throws OFMPException
    {
        assertSimulationState();

        Calendar calendar = new OFMPCalendar(m_Current);
        calendar.add(Calendar.HOUR, 1);

        m_Current = calendar.getTime();

        log.info("Set current date to " + m_Current);
        m_DateService.setCurrent(m_Current);
    }

    public void rollByDay()
    {
        assertSimulationState();

        Calendar calendar = new OFMPCalendar(m_Current);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        m_Current = calendar.getTime();
        log.info("Set current date to " + m_Current);
        m_DateService.setCurrent(m_Current);
    }

    public void rollNumberOfBusinessDays(int aCount) throws OFMPException
    {
        assertSimulationState();

        while (aCount > 0)
        {
            rollByDay();
            aCount--;
        }
    }

    public void rollToTheBeginningOfNextBusinessDay()
    {
        assertSimulationState();

        DateCalculator<Date> calculator = null;
        try
        {
            calculator = m_CalendarService.getDateCalculator();
        }
        catch (OFMPException aEx)
        {
            aEx.printStackTrace();
        }

        calculator.setCurrentBusinessDate(m_Current);
        calculator.moveByBusinessDays(1);

        Calendar calendar = new OFMPCalendar(calculator.getCurrentBusinessDate());
        calendar.set(Calendar.HOUR_OF_DAY, m_DateService.getDayStart());

        m_Current = calendar.getTime();
        log.info("Set current date to " + m_Current);
        m_DateService.setCurrent(m_Current);
    }

    public void rollToTheEndOfCurrentBusinessDay()
    {
        assertSimulationState();

        Calendar calendar = new OFMPCalendar(m_Current);
        calendar.set(Calendar.HOUR, m_DateService.getDayEnd());

        m_Current = calendar.getTime();
        log.info("Set current date to " + m_Current);
        m_DateService.setCurrent(m_Current);
    }

    public void setDateService(DateLocalService aDateService)
    {
        m_DateService = aDateService;
    }

    public void setEnabled(boolean aEnabled)
    {
        m_Enabled = aEnabled;
    }

    private void assertSimulationState()
    {
        boolean dateTriggerEnabled = false;

        String dateTriggerEnabledProperty = System.getProperty("ofmp.scheduler.date.start");

        if (dateTriggerEnabledProperty != null)
            dateTriggerEnabled = Boolean.getBoolean(dateTriggerEnabledProperty);

        if (!m_Enabled || dateTriggerEnabled)
            throw new IllegalStateException(
                    "Either the simulation service is not enabled or the system date trigger is enabled.");
    }

    public CalendarService getCalendarService()
    {
        return m_CalendarService;
    }

    public void setCalendarService(CalendarService aCalendarService)
    {
        m_CalendarService = aCalendarService;
    }

    public Date getCurrent()
    {
        Date date = null;
        try
        {
            date = m_DateService.getBusinessDayDate();
        }
        catch (OFMPException aEx)
        {
            aEx.printStackTrace();
        }

        return date;
    }
}
