package org.eclipse.ofmp.currency.dom;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.ofmp.common.dom.OFMPMathContext;

public class CurrencyExchange
{
    private CurrencyExchangeRate m_ExchangeRate;

    private MathContext m_Context = new MathContext(6, RoundingMode.HALF_UP);

    public CurrencyExchange(CurrencyExchangeRate aExchangeRate)
    {
        m_ExchangeRate = aExchangeRate;
    }

    public Money exchange(Money aMoney)
    {
        if (aMoney.getCurrency().equals(m_ExchangeRate.getFirst()))
        {
            BigDecimal amount = aMoney.getAmount().multiply(m_ExchangeRate.getRate(), m_Context);

            return new Money(amount, m_ExchangeRate.getSecond());
        }
        else if (aMoney.getCurrency().equals(m_ExchangeRate.getSecond()))
        {
            BigDecimal amount = aMoney.getAmount().divide(m_ExchangeRate.getRate(), OFMPMathContext.numberScale,
                    m_Context.getRoundingMode());

            return new Money(amount, m_ExchangeRate.getFirst());
        }
        else
            throw new RuntimeException("invalid currency: " + aMoney.getCurrency());
    }
}
