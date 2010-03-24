package org.eclipse.ofmp.date.dom;

import java.io.Serializable;
import java.util.Date;

import org.eclipse.ofmp.currency.dom.Currency;

public class CalendarEvent implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Currency m_Currency;

    private String m_Country, m_FinancialCenter, m_Name;

    private Date m_Date;

    public String getCountry()
    {
        return m_Country;
    }

    public void setCountry(String aCountry)
    {
        m_Country = aCountry;
    }

    public Currency getCurrency()
    {
        return m_Currency;
    }

    public void setCurrency(Currency aCurrency)
    {
        m_Currency = aCurrency;
    }

    public Date getDate()
    {
        return m_Date;
    }

    public void setDate(Date aDate)
    {
        m_Date = aDate;
    }

    public String getFinancialCenter()
    {
        return m_FinancialCenter;
    }

    public void setFinancialCenter(String aFinancialCenter)
    {
        m_FinancialCenter = aFinancialCenter;
    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String aName)
    {
        m_Name = aName;
    }
}
