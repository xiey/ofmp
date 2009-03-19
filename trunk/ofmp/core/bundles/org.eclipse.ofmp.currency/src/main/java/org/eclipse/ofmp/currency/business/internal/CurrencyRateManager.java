package org.eclipse.ofmp.currency.business.internal;

import java.math.BigDecimal;

import org.eclipse.ofmp.common.dom.OFMPMathContext;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyRate;

public class CurrencyRateManager
{
    private static CurrencyRateManager instance;

    private CurrencyRateManager()
    {
    }

    public static CurrencyRateManager getInstance()
    {
        if (null == instance)
        {
            instance = new CurrencyRateManager();
        }
        return instance;
    }

    public void validate(CurrencyRate aCurrencyRate)
    {
        assert aCurrencyRate != null;

        if (aCurrencyRate.getFixingDate() == null)
            throw new IllegalStateException("Fixing Date is mandatory.");

        if (aCurrencyRate.getCurrency() == null || aCurrencyRate.getCurrency() != null
                && aCurrencyRate.getCurrency().equals(Currency.NO_CURRENCY))
            throw new IllegalStateException("Currency is mandatory.");

        if (aCurrencyRate.getRate() == null || aCurrencyRate.getRate() != null
                && aCurrencyRate.getRate().floatValue() <= 0)
            throw new IllegalStateException(aCurrencyRate.getCurrency().getISOCode()
                    + " rate must be strictly greather than zero.");

    }

    public BigDecimal convertToReportingCurrency(BigDecimal amount, CurrencyRate currencyRate)
    {
        assert amount != null;
        assert currencyRate != null;

        return amount.divide(currencyRate.getRate(), OFMPMathContext.numberScale, OFMPMathContext.getContext()
                .getRoundingMode());
    }

}
