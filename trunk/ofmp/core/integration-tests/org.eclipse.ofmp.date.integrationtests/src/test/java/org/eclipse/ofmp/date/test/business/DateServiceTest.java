package org.eclipse.ofmp.date.test.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.objectlab.kit.datecalc.common.DateCalculator;

import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.date.business.CalendarService;
import org.eclipse.ofmp.date.business.DateLocalService;
import org.eclipse.ofmp.date.business.TimeFlowSimulationBeanMBean;
import org.eclipse.ofmp.security.business.AuthenticationService;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;

public class DateServiceTest extends AbstractOFMPSpringDMTest
{
    private DateLocalService m_Service;
    private CalendarService m_Calendar;
    private TimeFlowSimulationBeanMBean m_TimeFlowSimulationBean;

    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
        { "date-context-test.xml" };
    }

    @Override
    protected String getManifestLocation()
    {
        return "classpath:org/eclipse/ofmp/date/test/MANIFEST.MF";
    }

    @Override
    protected String[] getTestBundlesNames()
    {
        List<String> list = new ArrayList<String>(Arrays.asList(super.getTestBundlesNames()));

        list.add("org.eclipse.ofmp,org.eclipse.ofmp.test,1.0.0-M1");
        list.add("org.eclipse.ofmp,org.eclipse.ofmp.security,1.0.0-M1");
        list.add("org.eclipse.ofmp,org.eclipse.ofmp.currency,1.0.0-M1");
        list.add("org.eclipse.ofmp,org.eclipse.ofmp.date,1.0.0-M1");

        return list.toArray(new String[] {});
    }

    @Override
    protected void onSetUp() throws Exception
    {
        super.onSetUp();

        authenticateAs("ofmp-test-mo");
    }

    public void testThis() throws Exception
    {
        DateCalculator<Date> calculator = m_Calendar.getDateCalculator(new Currency("EUR"));

        Calendar calendar = new GregorianCalendar();
        calendar.set(2006, 05, 13);

        calculator.setCurrentBusinessDate(calendar.getTime());
        calculator.moveByBusinessDays(10);

        Calendar newCalendar = new GregorianCalendar();
        newCalendar.set(2006, 05, 27);
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        newCalendar.set(Calendar.MILLISECOND, 0);

        assertEquals(newCalendar.getTime(), calculator.getCurrentBusinessDate());
    }

    // /**
    // * FIXME: issue with User Transaction
    // */
    // public void testGetLastBusinessDayDate() throws Exception
    // {
    // // Friday 15 february 2008
    // m_TimeFlowSimulationBean.initialize(DateUtils.getDate(2008, 2, 15, 9, 0, 0), false);
    //
    // m_TimeFlowSimulationBean.rollToTheEndOfCurrentBusinessDay();
    // m_TimeFlowSimulationBean.rollByDay();
    //
    // assertEquals(DateUtils.getDate(2008, 2, 16), m_Service.getCurrentDate());
    // assertEquals(DateUtils.getDate(2008, 2, 15), m_Service.getLastBusinessDayDate());
    //
    // // Monday 18 february 2008
    // m_TimeFlowSimulationBean.initialize(DateUtils.getDate(2008, 2, 18, 9, 0, 0), false);
    //
    // assertEquals(DateUtils.getDate(2008, 2, 18), m_Service.getCurrentDate());
    // assertEquals(DateUtils.getDate(2008, 2, 15), m_Service.getLastBusinessDayDate());
    //
    // m_TimeFlowSimulationBean.rollToTheEndOfCurrentBusinessDay();
    // }
    //
    // /**
    // * FIXME: issue with User Transaction
    // */
    // public void testGetNextBusinessDayDate() throws Exception
    // {
    // m_Service.setBusinessDateOverride(null);
    //
    // // Friday 15 february 2008
    // m_TimeFlowSimulationBean.initialize(DateUtils.getDate(2008, 2, 15, 9, 0, 0), false);
    //
    // m_TimeFlowSimulationBean.rollToTheEndOfCurrentBusinessDay();
    // m_TimeFlowSimulationBean.rollByDay();
    //
    // assertEquals(DateUtils.getDate(2008, 2, 16), m_Service.getCurrentDate());
    // assertEquals(DateUtils.getDate(2008, 2, 18), m_Service.getNextBusinessDayDate());
    //
    // // Monday 18 february 2008
    // m_TimeFlowSimulationBean.initialize(DateUtils.getDate(2008, 2, 18, 9, 0, 0), false);
    //
    // assertEquals(DateUtils.getDate(2008, 2, 18), m_Service.getCurrentDate());
    // assertEquals(DateUtils.getDate(2008, 2, 19), m_Service.getNextBusinessDayDate());
    // }

    public void setAuthenticationService(AuthenticationService aAuthenticationService)
    {
        m_AuthenticationService = aAuthenticationService;
    }

    public void setService(DateLocalService aService)
    {
        m_Service = aService;
    }

    public void setCalendar(CalendarService aCalendar)
    {
        m_Calendar = aCalendar;
    }

    public void setTimeFlowSimulationBean(TimeFlowSimulationBeanMBean aTimeFlowSimulationBean)
    {
        m_TimeFlowSimulationBean = aTimeFlowSimulationBean;
    }

}
