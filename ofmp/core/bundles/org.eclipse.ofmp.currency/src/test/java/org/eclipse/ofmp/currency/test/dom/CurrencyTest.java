package org.eclipse.ofmp.currency.test.dom;

import junit.framework.TestCase;

import org.eclipse.ofmp.currency.dom.Currency;

import com.gargoylesoftware.base.testing.EqualsTester;

public class CurrencyTest extends TestCase
{
    public final void testEqualHashCode()
    {
        Currency currency1 = new Currency();
        Currency currency2 = new Currency();

        assertEquals(currency1.hashCode(), currency2.hashCode());
    }

    public final void testDifferentHashCode()
    {
        Currency currency1 = new Currency();
        Currency currency2 = new Currency();
        currency2.setISOCode("BEF");

        assertFalse(currency1.hashCode() == currency2.hashCode());
    }

    public final void testEquals()
    {
        Currency currency1 = new Currency();
        Currency currency2 = new Currency();
        Currency currency3 = new Currency();
        currency3.setISOCode("BEF");

        Object currency4 = new Object();

        // a is a control object against which the other three objects are to be compared.
        // b is a different object in memory than a but equal to a according to its value.
        // c is expected not to be equal to a.
        // If the class you are testing is final—cannot be subclassed—then d ought to
        // be null; otherwise, d represents an object that looks equal to a but is not. By
        // “looks equal,” we mean (for example) that d has the same properties as a, but
        // because d has additional properties through subclassing, it is not equal to a.
        new EqualsTester(currency1, currency2, currency3, currency4);
    }

}
