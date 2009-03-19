package org.eclipse.ofmp.currency.test.dom;

import junit.framework.TestCase;

import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyRate;

import com.gargoylesoftware.base.testing.EqualsTester;

public class CurrencyRateTest extends TestCase
{
    public final void testEqualHashCode()
    {
        CurrencyRate currencyRate1 = new CurrencyRate();
        CurrencyRate currencyRate2 = new CurrencyRate();

        assertEquals(currencyRate1.hashCode(), currencyRate2.hashCode());
    }

    public final void testDifferentHashCode()
    {
        CurrencyRate currencyRate1 = new CurrencyRate();
        CurrencyRate currencyRate2 = new CurrencyRate();
        currencyRate2.setCurrency(new Currency("BEF"));

        assertFalse(currencyRate1.hashCode() == currencyRate2.hashCode());
    }

    public final void testEquals()
    {
        CurrencyRate currencyRate1 = new CurrencyRate();
        CurrencyRate currencyRate2 = new CurrencyRate();
        CurrencyRate currencyRate3 = new CurrencyRate();
        currencyRate3.setCurrency(new Currency("BEF"));

        Object currencyRate4 = new Object();

        // a is a control object against which the other three objects are to be compared.
        // b is a different object in memory than a but equal to a according to its value.
        // c is expected not to be equal to a.
        // If the class you are testing is final—cannot be subclassed—then d ought to
        // be null; otherwise, d represents an object that looks equal to a but is not. By
        // “looks equal,” we mean (for example) that d has the same properties as a, but
        // because d has additional properties through subclassing, it is not equal to a.
        new EqualsTester(currencyRate1, currencyRate2, currencyRate3, currencyRate4);
    }

}
