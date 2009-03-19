package org.eclipse.ofmp.currency.dom;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.ofmp.common.dom.OFMPMathContext;
import org.eclipse.ofmp.common.dom.ForexTradeMessage.RateDirection;
import org.eclipse.ofmp.common.utils.OFMPToStringBuilder;

public class Money implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Currency m_Currency;
    private BigDecimal m_Amount;

    private final int DECIMAL_SCALE = OFMPMathContext.numberScale;
    private final RoundingMode ROUNDING_MODE = OFMPMathContext.defaultRoundingMode;

    public Money()
    {
        m_Amount = BigDecimal.ZERO;
        m_Amount = m_Amount.setScale(DECIMAL_SCALE, ROUNDING_MODE);
        m_Currency = Currency.NO_CURRENCY;
    }

    public Money(BigDecimal aAmount)
    {
        m_Amount = aAmount.setScale(DECIMAL_SCALE, ROUNDING_MODE);
        m_Currency = Currency.NO_CURRENCY;
    }

    public Money(Currency aCurrency)
    {
        m_Amount = BigDecimal.ZERO;
        m_Amount = m_Amount.setScale(DECIMAL_SCALE, ROUNDING_MODE);
        m_Currency = aCurrency;
    }

    public Money(Currency aCurrency, int scale)
    {
        m_Amount = BigDecimal.ZERO;
        m_Amount = m_Amount.setScale(scale, ROUNDING_MODE);
        m_Currency = aCurrency;
    }

    /**
     * A copy constructor to clone this object.
     * 
     * @param aMoney
     */
    public Money(Money aMoney)
    {
        assert aMoney != null;

        m_Amount = aMoney.m_Amount == null ? BigDecimal.ZERO : aMoney.m_Amount.plus();
        m_Currency = new Currency(aMoney.m_Currency);
    }

    public Money(BigDecimal aAmount, Currency aCurrency)
    {
        m_Amount = aAmount.setScale(DECIMAL_SCALE, ROUNDING_MODE);
        m_Currency = aCurrency;
    }

    public Money(BigDecimal aAmount, Currency aCurrency, int scale)
    {
        m_Amount = aAmount.setScale(scale, ROUNDING_MODE);
        m_Currency = aCurrency;
    }

    public Currency getCurrency()
    {
        return m_Currency;
    }

    public BigDecimal getAmount()
    {
        return m_Amount;
    }

    /**
     * Needed for IBatis mapping. As of v2.2, Ibatis supports immutable objects thru private setters.
     * 
     * @param aAmount
     */
    @SuppressWarnings("unused")
    private void setAmount(BigDecimal aAmount)
    {
        m_Amount = aAmount;
    }

    /**
     * Needed for IBatis mapping. As of v2.2, Ibatis supports immutable objects thru private setters.
     * 
     * @param aAmount
     */
    @SuppressWarnings("unused")
    private void setCurrency(Currency aCurrency)
    {
        m_Currency = aCurrency;
    }

    public Money add(Money aMoney)
    {
        assert m_Currency.equals(aMoney.m_Currency);

        return new Money(m_Amount.add(aMoney.m_Amount), m_Currency);
    }

    public Money subtract(Money aMoney)
    {
        assert m_Currency.equals(aMoney.m_Currency);

        return new Money(m_Amount.subtract(aMoney.m_Amount), m_Currency);
    }

    public Money divide(BigDecimal aAmount)
    {
        assert aAmount.floatValue() != 0;

        return new Money(m_Amount.divide(aAmount, OFMPMathContext.numberScale,
                OFMPMathContext.defaultRoundingMode), m_Currency);
    }

    @SuppressWarnings("unused")
    private Money multiply(BigDecimal aAmount)
    {
        return new Money(m_Amount.multiply(aAmount, OFMPMathContext.getContext()), m_Currency);
    }

    public Money convert(BigDecimal aRate, RateDirection rateDirection, Currency aCurrency, MathContext aMathContext)
    {
        if (rateDirection == RateDirection.NORMAL)
            return new Money(m_Amount.multiply(aRate, aMathContext), aCurrency);
        else
            return aRate.compareTo(BigDecimal.ZERO) == 0 ? new Money(BigDecimal.ZERO, aCurrency) : new Money(m_Amount
                    .divide(aRate, OFMPMathContext.numberScale, aMathContext.getRoundingMode()), aCurrency);
    }

    public Money convert(BigDecimal aRate, RateDirection rateDirection, Currency aCurrency)
    {
        return convert(aRate, rateDirection, aCurrency, OFMPMathContext.getContext());
    }

    public Money negate()
    {
        return new Money(m_Amount.negate(), m_Currency);
    }

    public boolean equals(Object aOther)
    {
        if (aOther instanceof Money == false)
            return false;

        Money other = (Money) aOther;

        return new EqualsBuilder().append(m_Currency, other.m_Currency).isEquals()
                && (m_Amount.compareTo(other.m_Amount) == 0);
    }

    public int hashCode()
    {
        return new HashCodeBuilder(7, 13).append(m_Currency).append(m_Amount).toHashCode();
    }

    public String toString()
    {
        return build(new OFMPToStringBuilder()).toString();
    }

    public OFMPToStringBuilder build(OFMPToStringBuilder aBuilder)
    {
        aBuilder.append("Amount", m_Amount).appendNested("Currency", m_Currency.build(aBuilder.indent()));

        return aBuilder;
    }

}