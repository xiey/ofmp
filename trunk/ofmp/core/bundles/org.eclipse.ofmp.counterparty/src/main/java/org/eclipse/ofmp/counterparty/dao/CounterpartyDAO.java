package org.eclipse.ofmp.counterparty.dao;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Counterparty;
import org.eclipse.ofmp.counterparty.dom.Customer;

public interface CounterpartyDAO
{
    Counterparty create(Counterparty counterparty) throws SQLException;

    Bank createBank(Bank bank) throws SQLException;

    Customer createCustomer(Customer customer) throws SQLException;

    Counterparty updateCounterparty(Counterparty counterparty) throws SQLException;

    Bank updateBank(Bank bank) throws SQLException;

    Customer updateCustomer(Customer customer) throws SQLException;

    List<Bank> selectBanks() throws SQLException;

    List<Customer> selectCustomers() throws SQLException;

    Bank findBankById(int id) throws SQLException;

    Bank findBankByReutersCode(String aSystemId, String aCode) throws SQLException;

    Customer findCustomerById(int id) throws SQLException;

    Customer findCustomerByName(String name) throws SQLException;

    Customer findCustomerByShortName(String aShortName) throws SQLException;
}