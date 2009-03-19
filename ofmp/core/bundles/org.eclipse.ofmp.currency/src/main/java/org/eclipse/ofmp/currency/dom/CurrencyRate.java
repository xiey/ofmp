package org.eclipse.ofmp.currency.dom;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.ofmp.common.utils.OFMPToStringBuilder;

public class CurrencyRate implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Currency m_Currency;
    private Date m_FixingDate;
    private BigDecimal m_Rate;
    private Integer m_Generation;

    public CurrencyRate()
    {
        m_Currency = new Currency();
        m_FixingDate = DateUtils.truncate(new Date(), Calendar.DATE);
        m_Rate = BigDecimal.ZERO;
    }

    public CurrencyRate(Currency aCurrency)
    {
        m_Currency = aCurrency;
        m_FixingDate = DateUtils.truncate(new Date(), Calendar.DATE);
        m_Rate = BigDecimal.ZERO;
    }

    /**
     * A copy constructor to clone this object.
     * 
     * @param aCurrencyRate
     *            to be cloned
     */
    public CurrencyRate(final CurrencyRate aCurrencyRate)
    {
        m_Currency = new Currency(aCurrencyRate.getCurrency());
        m_FixingDate = aCurrencyRate.getFixingDate();
        m_Rate = aCurrencyRate.getRate() == null ? BigDecimal.ZERO : aCurrencyRate.getRate().plus();
    }

    public Currency getCurrency()
    {
        return m_Currency;
    }

    public void setCurrency(Currency aCurrencyCode)
    {
        m_Currency = aCurrencyCode;
    }

    public final Date getFixingDate()
    {
        return m_FixingDate;
    }

    public final void setFixingDate(Date fixingDate)
    {
        this.m_FixingDate = fixingDate;
    }

    public final BigDecimal getRate()
    {
        return m_Rate;
    }

    public final void setRate(BigDecimal rate)
    {
        this.m_Rate = rate;
    }

    public Integer getGeneration()
    {
        return m_Generation;
    }

    public void setGeneration(Integer aGeneration)
    {
        m_Generation = aGeneration;
    }

    public boolean isNew()
    {
        return m_Generation == null;
    }

    @Override
    public boolean equals(Object aOther)
    {
        if (aOther instanceof CurrencyRate == false)
            return false;

        CurrencyRate other = (CurrencyRate) aOther;

        return new EqualsBuilder().append(m_Currency, other.m_Currency).append(m_FixingDate, other.m_FixingDate)
                .append(m_Rate, other.m_Rate).isEquals();

    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(11, 3).append(m_Currency).append(m_FixingDate).append(m_Rate).append(m_Generation)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return build(new OFMPToStringBuilder()).toString();
    }

    public OFMPToStringBuilder build(OFMPToStringBuilder aBuilder)
    {
        aBuilder.append("Currency", m_Currency.getISOCode()).append("Fixing Date", m_FixingDate).append("Rate", m_Rate)
                .append("Generation", m_Generation);

        return aBuilder;
    }

}
