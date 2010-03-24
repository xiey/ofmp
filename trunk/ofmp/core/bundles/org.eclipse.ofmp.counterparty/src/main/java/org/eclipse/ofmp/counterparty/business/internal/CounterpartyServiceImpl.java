package org.eclipse.ofmp.counterparty.business.internal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.common.exception.StatusFactory;
import org.eclipse.ofmp.counterparty.business.CounterpartyService;
import org.eclipse.ofmp.counterparty.dao.CounterpartyDAO;
import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Counterparty;
import org.eclipse.ofmp.counterparty.dom.Customer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = OFMPException.class)
public class CounterpartyServiceImpl implements CounterpartyService, InitializingBean
{
    private final Logger log = Logger.getLogger(getClass());

    private CounterpartyDAO m_DAO;

    private Integer m_InstanceCounterpartyId;

    private Bank m_InstanceCounterparty;

    public void afterPropertiesSet() throws Exception
    {
        Validate.notNull(m_InstanceCounterpartyId, "Instance Counterparty Id must be provided.");

        m_InstanceCounterparty = m_DAO.findBankById(m_InstanceCounterpartyId);

        if (m_InstanceCounterparty == null || !(m_InstanceCounterparty instanceof Bank))
            log.error("Bank with id " + m_InstanceCounterpartyId + " not found in the system.");

        // throw new OFMPException(StatusFactory.ILLEGAL_ARGUMENT, new Throwable("Bank with id "
        // + m_InstanceCounterpartyId + " not found in the system."));
    }

    public Counterparty create(Counterparty aCounterparty) throws OFMPException
    {
        log.debug("Creating counterparty " + aCounterparty);

        try
        {
            if (aCounterparty instanceof Bank)
            {
                m_DAO.create(aCounterparty);
                m_DAO.createBank((Bank) aCounterparty);
            }
            else if (aCounterparty instanceof Customer)
            {
                Customer customer = (Customer) aCounterparty;

                if (m_DAO.findCustomerByName(customer.getName()) != null)
                    throw new OFMPException(StatusFactory.CUSTOMER_NAME_CONFLICT);

                m_DAO.create(aCounterparty);
                m_DAO.createCustomer(customer);
            }
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return findCounterparty(aCounterparty.getId());
    }

    public Counterparty update(Counterparty aCounterparty) throws OFMPException
    {
        log.debug("Updating counterparty " + aCounterparty);

        Counterparty original = findCounterparty(aCounterparty.getId());
        if (original == null)
            throw new OFMPException(StatusFactory.OBJECT_NOT_FOUND);

        if (!original.getGeneration().equals(aCounterparty.getGeneration()))
            throw new OFMPException(StatusFactory.CONCURRENT_MODIFICATION);

        try
        {

            if (aCounterparty instanceof Bank)
            {
                m_DAO.updateCounterparty(aCounterparty);
                // m_DAO.updateBank((Bank) aCounterparty);
            }
            else if (aCounterparty instanceof Customer)
            {

                Customer customer = (Customer) aCounterparty;

                Customer customerLoadedByName = m_DAO.findCustomerByName(customer.getName());

                if (customerLoadedByName != null && !customerLoadedByName.getId().equals(customer.getId()))
                    throw new OFMPException(StatusFactory.CUSTOMER_NAME_CONFLICT);

                m_DAO.updateCounterparty(aCounterparty);
                m_DAO.updateCustomer(customer);
            }
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return findCounterparty(aCounterparty.getId());
    }

    public List<Bank> enumerateBanks() throws OFMPException
    {
        try
        {
            return m_DAO.selectBanks();
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public List<Customer> enumerateCustomers() throws OFMPException
    {
        try
        {
            return m_DAO.selectCustomers();
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public List<Counterparty> enumerateCounterparties() throws OFMPException
    {
        List<Counterparty> result = new ArrayList<Counterparty>();
        try
        {
            result.addAll(m_DAO.selectBanks());
            result.addAll(m_DAO.selectCustomers());
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return result;
    }

    public Counterparty findCounterparty(Integer aId) throws OFMPException
    {
        Counterparty result = null;

        try
        {
            result = m_DAO.findBankById(aId);
            if (result == null)
                result = m_DAO.findCustomerById(aId);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return result;
    }

    public Bank findBank(Integer aId) throws OFMPException
    {
        try
        {
            return m_DAO.findBankById(aId);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public Customer findCustomerByName(String aName) throws OFMPException
    {
        try
        {
            return m_DAO.findCustomerByName(aName);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public Customer findCustomerByShortName(String aShortName) throws OFMPException
    {
        try
        {
            return m_DAO.findCustomerByShortName(aShortName);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public Bank findBankByExternalId(String aSystemId, String aCode) throws OFMPException
    {
        try
        {
            return m_DAO.findBankByReutersCode(aSystemId, aCode);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public Bank getInstanceCounterparty() throws OFMPException
    {
        return m_InstanceCounterparty;
    }

    public void setDAO(CounterpartyDAO aCounterpartyDAO)
    {
        m_DAO = aCounterpartyDAO;
    }

    public void setInstanceCounterpartyId(Integer aInstanceCounterpartyId)
    {
        m_InstanceCounterpartyId = aInstanceCounterpartyId;
    }

}