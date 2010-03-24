package org.eclipse.ofmp.date.business.internal;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.objectlab.kit.datecalc.common.DateCalculator;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.common.exception.StatusFactory;
import org.eclipse.ofmp.common.utils.OFMPCalendar;
import org.eclipse.ofmp.date.business.CalendarLocalService;
import org.eclipse.ofmp.date.business.DateListener;
import org.eclipse.ofmp.date.business.DateLocalService;
import org.eclipse.ofmp.date.dao.DateDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class DateServiceImpl implements DateLocalService, InitializingBean
{
    private final Logger log = Logger.getLogger(getClass());

    private final Format m_DebugFormat = new SimpleDateFormat("yyyy-MM-dd(E) HH:mm:ss");

    private final List<DateListener> m_DateListeners = new ArrayList<DateListener>();

    private Date m_BusinessDayDate, m_Current, m_BusinessDateOverride;

    private DateCalculator<Date> m_Calculator;
    private DateCalculator<Date> m_ReverseCalculator;

    private DateDAO m_DateDAO;
    private CalendarLocalService m_CalendarService;

    private PlatformTransactionManager m_TransactionManager;

    private int m_DayStart = 0;
    private int m_DayEnd = 23;

    public void afterPropertiesSet() throws Exception
    {
        m_BusinessDayDate = m_DateDAO.readBusinessDay();
        m_Calculator = m_CalendarService.getDateCalculator();
        m_ReverseCalculator = m_CalendarService.getReverseDateCalculator();
    }

    public Date getBusinessDayDate() throws OFMPException
    {
        if (m_Current == null || m_BusinessDayDate == null)
            throw new OFMPException(StatusFactory.ST07001);

        if (m_BusinessDateOverride != null)
            return m_BusinessDateOverride;

        return m_BusinessDayDate;
    }

    public Date getLastBusinessDayDate() throws OFMPException
    {
        try
        {
            if (isBusinessDay())
            {
                m_ReverseCalculator.setCurrentBusinessDate(getBusinessDayDate());
                m_ReverseCalculator.moveByBusinessDays(-1);
                return m_ReverseCalculator.getCurrentBusinessDate();
            }
            else
                return m_DateDAO.readBusinessDay();

        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public Date getNextBusinessDayDate() throws OFMPException
    {
        if (isBusinessDay())
            return addWorkingDays(m_Current, 1);
        else
            return addWorkingDays(m_Current, 0);
    }

    public boolean isBusinessDay()
    {
        return m_Current != null && m_BusinessDayDate != null;
    }

    public Date getCurrentDate() throws OFMPException
    {
        if (m_Current == null)
            throw new OFMPException(StatusFactory.ST07002);

        return DateUtils.truncate(m_Current, Calendar.DATE);
    }

    public Date getCurrentTimestamp() throws OFMPException
    {
        if (m_Current == null)
            throw new OFMPException(StatusFactory.ST07002);

        return m_Current;
    }

    public Date addWorkingDays(int aNumberOfDays) throws OFMPException
    {
        Date result = null;

        if (aNumberOfDays >= 0)
        {
            m_Calculator.setCurrentBusinessDate(getBusinessDayDate());
            m_Calculator.moveByBusinessDays(aNumberOfDays);
            result = m_Calculator.getCurrentBusinessDate();
        }
        else
        {
            m_ReverseCalculator.setCurrentBusinessDate(getBusinessDayDate());
            m_ReverseCalculator.moveByBusinessDays(aNumberOfDays);
            result = m_ReverseCalculator.getCurrentBusinessDate();
        }

        return result;
    }

    public Date addWorkingDays(Date aDate, int aNumberOfDays) throws OFMPException
    {
        Date result = null;

        if (aNumberOfDays >= 0)
        {
            m_Calculator.setCurrentBusinessDate(aDate);
            m_Calculator.moveByBusinessDays(aNumberOfDays);
            result = m_Calculator.getCurrentBusinessDate();
        }
        else
        {
            m_ReverseCalculator.setCurrentBusinessDate(aDate);
            m_ReverseCalculator.moveByBusinessDays(aNumberOfDays);
            result = m_ReverseCalculator.getCurrentBusinessDate();
        }

        return result;
    }

    public synchronized void setCurrent(Date aDate)
    {
        setCurrent(aDate, true);
    }

    public synchronized void setCurrent(Date aDate, Boolean aFireListeners)
    {
        m_Current = aDate;

        // get proposed business day for the current time
        Date newDate = proposeBusinessDay(aDate);

        // we are off now?
        if (m_BusinessDayDate == null)
        {
            // and new day should start
            if (newDate != null)
            {
                // start it
                internalSetBusinessDate(newDate, aFireListeners);
            }
        }
        else
        {// we are in business day
            // and it is later the business day (otherwise just wait)
            if (aDate.after(m_BusinessDayDate))
            {
                // we are in business day
                if (newDate != null)
                {
                    // and it is different from current
                    if (!newDate.equals(m_BusinessDayDate))
                    {
                        // go ahead to the proposed date
                        m_Calculator.setCurrentBusinessDate(m_BusinessDayDate);
                        m_Calculator.moveByBusinessDays(1);

                        // including missed days
                        while (m_Calculator.getCurrentBusinessDate().compareTo(newDate) <= 0)
                        {
                            internalSetBusinessDate(null, aFireListeners);
                            internalSetBusinessDate(m_Calculator.getCurrentBusinessDate(), aFireListeners);

                            m_Calculator.moveByBusinessDays(1);
                        }
                    }
                }
                else
                {
                    // we can change business day only, when previous day is over
                    if (!DateUtils.truncate(aDate, Calendar.DATE).equals(m_BusinessDayDate)
                            || getHour(aDate) >= m_DayEnd)
                        internalSetBusinessDate(null, aFireListeners);
                }
            }
        }
    }

    private void internalSetBusinessDate(Date aDate, Boolean aFireListeners)
    {
        if (aDate == null)
        {

            if (aFireListeners)
                for (DateListener listener : m_DateListeners)
                    listener.beforeBusinessDayEnd(aDate);

            log.info(m_DebugFormat.format(m_Current) + " - ending business day : "
                    + m_DebugFormat.format(m_BusinessDayDate));

            m_BusinessDayDate = null;

            if (aFireListeners)
                for (DateListener listener : m_DateListeners)
                    listener.afterBusinessDayEnd(aDate);
        }
        else
        {
            if (aFireListeners)
                for (DateListener listener : m_DateListeners)
                    listener.beforeBusinessDayBegin(aDate);

            m_BusinessDayDate = DateUtils.truncate(aDate, Calendar.DATE);

            log.info(m_DebugFormat.format(m_Current) + " - starting business day : "
                    + m_DebugFormat.format(m_BusinessDayDate));

            if (aFireListeners)
                for (DateListener listener : m_DateListeners)
                    listener.afterBusinessDayBegin(aDate);
        }

        if (m_BusinessDayDate != null)
        {
            TransactionStatus status = m_TransactionManager.getTransaction(new DefaultTransactionDefinition());

            try
            {
                m_DateDAO.writeBusinessDay(m_BusinessDayDate);

                m_TransactionManager.commit(status);
            }
            catch (Throwable aEx)
            {
                if (status != null)
                    m_TransactionManager.rollback(status);

                log.error("Cannot write business day", aEx);
            }
        }
        else
        {
            log.info("Business Date is null. Skip writing it to DB.");
        }
    }

    private Date proposeBusinessDay(Date aCurrentDate)
    {
        if (m_Calculator.isNonWorkingDay(aCurrentDate))
            return null;
        else
        {
            int hour = getHour(aCurrentDate);

            if (hour >= m_DayStart && hour < m_DayEnd)
                return DateUtils.truncate(aCurrentDate, Calendar.DATE);
            else
                return null;
        }
    }

    private int getHour(Date aDate)
    {
        return new OFMPCalendar(aDate).get(Calendar.HOUR_OF_DAY);
    }

    public void addDateListener(DateListener aListener)
    {
        m_DateListeners.add(aListener);
    }

    public void removeDateListener(DateListener aListener)
    {
        m_DateListeners.remove(aListener);
    }

    public void setCalendarService(CalendarLocalService aCalendarService)
    {
        m_CalendarService = aCalendarService;
    }

    public void setDateDAO(DateDAO aDateDAO)
    {
        m_DateDAO = aDateDAO;
    }

    public void setDayEnd(int aDayEnd)
    {
        m_DayEnd = aDayEnd;
    }

    public int getDayEnd()
    {
        return m_DayEnd;
    }

    public void setDayStart(int aDayStart)
    {
        m_DayStart = aDayStart;
    }

    public int getDayStart()
    {
        return m_DayStart;
    }

    public void setTransactionManager(PlatformTransactionManager aTransactionManager)
    {
        m_TransactionManager = aTransactionManager;
    }

    public void setBusinessDateOverride(Date aBusinessDateOverride) throws OFMPException
    {
        m_BusinessDateOverride = aBusinessDateOverride;
    }

}
