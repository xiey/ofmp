package org.eclipse.ofmp.currency.dom;

import java.io.Serializable;

public class CurrencyPair implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final Currency m_FirstCurrency, m_SecondCurrency;

    public CurrencyPair(Currency aFirstCurrency, Currency aSecondCurrency)
    {
        if (aFirstCurrency == null)
            throw new IllegalArgumentException("The first currency must be provided.");

        if (aSecondCurrency == null)
            throw new IllegalArgumentException("The second currency must be provided.");

        m_FirstCurrency = aFirstCurrency;
        m_SecondCurrency = aSecondCurrency;
    }

    public Currency getFirstCurrency()
    {
        return m_FirstCurrency;
    }

    public Currency getSecondCurrency()
    {
        return m_SecondCurrency;
    }

    @Override
    public String toString()
    {
        return m_FirstCurrency.getISOCode() + "/" + m_SecondCurrency.getISOCode();
    }

}
