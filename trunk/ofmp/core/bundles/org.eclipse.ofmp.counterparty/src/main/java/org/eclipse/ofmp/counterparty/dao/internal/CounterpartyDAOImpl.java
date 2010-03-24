package org.eclipse.ofmp.counterparty.dao.internal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ofmp.counterparty.dao.CounterpartyDAO;
import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Counterparty;
import org.eclipse.ofmp.counterparty.dom.Customer;
import org.eclipse.ofmp.counterparty.dom.FrontOfficeIdentification;
import org.eclipse.ofmp.security.dom.ExternalCode;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CounterpartyDAOImpl extends SqlMapClientDaoSupport implements CounterpartyDAO
{
    public Counterparty create(Counterparty aCounterparty) throws SQLException
    {
        Integer pk = (Integer) getSqlMapClient().insert("Counterparty.insertCounterparty", aCounterparty);
        aCounterparty.setId(pk);

        return aCounterparty;
    }

    public Bank createBank(Bank aBank) throws SQLException
    {
        getSqlMapClient().insert("Counterparty.insertBank", aBank);

        Bank newInstance = new Bank(aBank);
        newInstance.setId(aBank.getId());

        // FIXME Move to the service
        insertCodes(aBank);

        return newInstance;
    }

    private void insertCodes(Bank aBank) throws SQLException
    {
        for (Map.Entry<String, FrontOfficeIdentification> foi : aBank.getFrontOfficeIdentifications().entrySet())
        {
            for (String code : foi.getValue())
            {
                ExternalCode ec = new ExternalCode();

                ec.setEntity(aBank.getId());
                ec.setCode(code);
                ec.setSystemId(foi.getKey());

                getSqlMapClient().insert("Counterparty.insertBankCode", ec);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, FrontOfficeIdentification> selectCodes(Integer aId) throws SQLException
    {
        Map<String, FrontOfficeIdentification> result = new HashMap<String, FrontOfficeIdentification>();

        for (ExternalCode code : (List<ExternalCode>) getSqlMapClient().queryForList("Counterparty.selectBankCodes",
                aId))
        {
            FrontOfficeIdentification foi = result.get(code.getSystemId());
            if (foi == null)
            {
                foi = new FrontOfficeIdentification(code.getSystemId());
                result.put(code.getSystemId(), foi);
            }

            foi.add(code.getCode());
        }

        return result;
    }

    public Customer createCustomer(Customer aCustomer) throws SQLException
    {
        getSqlMapClient().insert("Counterparty.insertCustomer", aCustomer);

        Customer newInstance = new Customer(aCustomer);
        newInstance.setId(aCustomer.getId());

        return newInstance;
    }

    public Customer findCustomerById(int id) throws SQLException
    {
        return (Customer) getSqlMapClient().queryForObject("Counterparty.selectCustomerById", id);
    }

    public Bank findBankByReutersCode(String aSystemId, String aCode) throws SQLException
    {
        ExternalCode code = new ExternalCode();

        code.setSystemId(aSystemId);
        code.setCode(aCode);

        Bank bank = (Bank) getSqlMapClient().queryForObject("Counterparty.selectBankByCode", code);

        // FIXME Move to the service
        if (bank != null)
            bank.setFrontOfficeIdentifications(selectCodes(bank.getId()));

        return bank;
    }

    public Customer findCustomerByName(String name) throws SQLException
    {
        return (Customer) getSqlMapClient().queryForObject("Counterparty.selectCustomerByName", name);
    }

    public Customer findCustomerByShortName(String name) throws SQLException
    {
        return (Customer) getSqlMapClient().queryForObject("Counterparty.selectCustomerByShortName", name);
    }

    public Bank findBankById(int id) throws SQLException
    {
        Bank bank = (Bank) getSqlMapClient().queryForObject("Counterparty.selectBankById", id);

        // FIXME Move to the service
        if (bank != null)
            bank.setFrontOfficeIdentifications(selectCodes(bank.getId()));

        return bank;
    }

    @SuppressWarnings("unchecked")
    public List<Customer> selectCustomers() throws SQLException
    {
        return getSqlMapClient().queryForList("Counterparty.selectCustomers");
    }

    @SuppressWarnings("unchecked")
    public List<Bank> selectBanks() throws SQLException
    {
        return getSqlMapClient().queryForList("Counterparty.selectBanks");
    }

    public Counterparty updateCounterparty(Counterparty aCounterparty) throws SQLException
    {
        getSqlMapClient().update("Counterparty.updateCounterparty", aCounterparty);

        return aCounterparty;
    }

    public Customer updateCustomer(Customer aCustomer) throws SQLException
    {
        getSqlMapClient().update("Counterparty.updateCustomer", aCustomer);

        return findCustomerById(aCustomer.getId());
    }

    public Bank updateBank(Bank aBank) throws SQLException
    {
        getSqlMapClient().update("Counterparty.updateBank", aBank);
        getSqlMapClient().delete("Counterparty.deleteBankCodes", aBank.getId());

        // FIXME Move to the service
        insertCodes(aBank);

        return aBank;
    }

}