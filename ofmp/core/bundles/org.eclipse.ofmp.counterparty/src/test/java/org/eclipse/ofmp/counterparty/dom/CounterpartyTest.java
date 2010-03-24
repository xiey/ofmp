package org.eclipse.ofmp.counterparty.dom;

import junit.framework.TestCase;

import org.eclipse.ofmp.counterparty.business.CounterpartyFactory;

import com.gargoylesoftware.base.testing.EqualsTester;

public class CounterpartyTest extends TestCase
{

    public final void testEqualHashCode()
    {
        Counterparty counterparty1 = CounterpartyFactory.createBank();
        Counterparty counterparty2 = CounterpartyFactory.createBank();

        assertEquals(counterparty1.hashCode(), counterparty2.hashCode());
    }

    public final void testDifferentHashCode()
    {
        Counterparty counterparty1 = CounterpartyFactory.createBank();
        Counterparty counterparty2 = CounterpartyFactory.createBank();
        counterparty2.setId(1);

        assertFalse(counterparty1.hashCode() == counterparty2.hashCode());
    }

    public final void testEquals()
    {
        Counterparty counterparty1 = CounterpartyFactory.createBank();
        Counterparty counterparty2 = CounterpartyFactory.createBank();
        Counterparty counterparty3 = CounterpartyFactory.createBank();
        counterparty3.setId(1);

        Object counterparty4 = new Object();

        new EqualsTester(counterparty1, counterparty2, counterparty3, counterparty4);
    }

}
