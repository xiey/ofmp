package org.eclipse.ofmp.currency.business;

import java.io.Serializable;
import java.util.Date;

import org.eclipse.ofmp.currency.dom.Currency;

public class CurrencyRateQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Currency m_Currency;
    private Date m_FixingDateFrom, m_FixingDateTo;

    public final Date getFixingDateFrom()
    {
        return m_FixingDateFrom;
    }

    public final void setFixingDateFrom(Date fixingDateFrom)
    {
        this.m_FixingDateFrom = fixingDateFrom;
    }

    public final Date getFixingDateTo()
    {
        return m_FixingDateTo;
    }

    public final void setFixingDateTo(Date fixingDateTo)
    {
        this.m_FixingDateTo = fixingDateTo;
    }

    public final Currency getCurrency()
    {
        return m_Currency;
    }

    public final void setCurrency(Currency currency)
    {
        m_Currency = currency;
    }

}
