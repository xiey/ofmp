package org.eclipse.ofmp.currency.dom;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CurrencyExchangeRate
{
    private Currency m_First, m_Second;
    private BigDecimal m_Rate;

    public CurrencyExchangeRate(Currency aFirst, Currency aSecond, BigDecimal aRate)
    {
        if (aFirst.equals(aSecond))
            assert m_Rate.equals(aRate);

        assert aRate.floatValue() >= 0;

        m_First = aFirst;
        m_Second = aSecond;
        m_Rate = aRate;
    }

    public BigDecimal getRate()
    {
        return m_Rate;
    }

    public Currency getFirst()
    {
        return m_First;
    }

    public Currency getSecond()
    {
        return m_Second;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("first", m_First).append("second", m_Second).append("rate", m_Rate)
                .toString();
    }

    public boolean equals(Object aOther)
    {
        if (aOther instanceof CurrencyExchangeRate == false)
            return false;

        CurrencyExchangeRate other = (CurrencyExchangeRate) aOther;

        return new EqualsBuilder().append(m_First, other.m_First).append(m_Second, other.m_Second).append(m_Rate,
                other.m_Rate).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder(17, 23).append(m_First).append(m_Second).append(m_Rate).toHashCode();
    }
}
