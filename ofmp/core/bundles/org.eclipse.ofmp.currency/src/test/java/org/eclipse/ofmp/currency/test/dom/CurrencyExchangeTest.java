package org.eclipse.ofmp.currency.test.dom;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyExchange;
import org.eclipse.ofmp.currency.dom.CurrencyExchangeRate;
import org.eclipse.ofmp.currency.dom.Money;

public class CurrencyExchangeTest extends TestCase
{

    public void testExchangeRate()
    {
        Currency usd = new Currency("USD");
        Currency eur = new Currency("EUR");

        CurrencyExchangeRate usd_eur = new CurrencyExchangeRate(usd, eur, new BigDecimal("0.75"));

        CurrencyExchange eur_usd_exchange = new CurrencyExchange(usd_eur);

        Money euroMoney = new Money(new BigDecimal(100), eur);

        Money usdMoney = new Money(new BigDecimal(100), usd);

        assertEquals(usd, eur_usd_exchange.exchange(euroMoney).getCurrency());
        assertEquals(new BigDecimal("133.33"), eur_usd_exchange.exchange(euroMoney).getAmount().stripTrailingZeros());

        assertEquals(eur, eur_usd_exchange.exchange(usdMoney).getCurrency());
        assertEquals(new BigDecimal("75"), eur_usd_exchange.exchange(usdMoney).getAmount().stripTrailingZeros());
    }

    public void testInfiniteExchangeRate()
    {
        Currency gbp = new Currency("GBP");
        Currency jpy = new Currency("JPY");

        CurrencyExchangeRate gbp_jpy = new CurrencyExchangeRate(gbp, jpy, new BigDecimal("300"));

        CurrencyExchange gbp_jpy_exchange = new CurrencyExchange(gbp_jpy);

        Money jpyMoney = new Money(new BigDecimal(100), jpy);

        Money gbpMoney = new Money(new BigDecimal(100), gbp);

        assertEquals(gbp, gbp_jpy_exchange.exchange(jpyMoney).getCurrency());
        assertEquals(new BigDecimal("0.33").toPlainString(), gbp_jpy_exchange.exchange(jpyMoney).getAmount()
                .stripTrailingZeros().toPlainString());

        assertEquals(jpy, gbp_jpy_exchange.exchange(gbpMoney).getCurrency());
        assertEquals(new BigDecimal("30000").toPlainString(), gbp_jpy_exchange.exchange(gbpMoney).getAmount()
                .stripTrailingZeros().toPlainString());
    }
}
