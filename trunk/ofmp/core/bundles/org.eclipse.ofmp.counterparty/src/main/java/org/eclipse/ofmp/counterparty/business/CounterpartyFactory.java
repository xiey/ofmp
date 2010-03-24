package org.eclipse.ofmp.counterparty.business;

import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Customer;

public class CounterpartyFactory
{
    public static Bank createBank()
    {
        Bank counterparty = new Bank();

        counterparty.setName("");

        return counterparty;
    }

    public static Customer createCustomer()
    {
        Customer counterparty = new Customer();

        counterparty.setName("");
        counterparty.setShortName("");

        return counterparty;
    }
}
