package org.eclipse.ofmp.counterparty.test.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.eclipse.ofmp.counterparty.business.CounterpartyFactory;
import org.eclipse.ofmp.counterparty.dao.CounterpartyDAO;
import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Counterparty;
import org.eclipse.ofmp.counterparty.dom.Customer;
import org.eclipse.ofmp.counterparty.dom.FrontOfficeIdentification;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;
import org.springframework.context.ApplicationContext;

public class CounterpartyDAOTest extends AbstractOFMPSpringDMTest
{
    private CounterpartyDAO m_DAO;
    private ApplicationContext m_CounterpartyBundleAppCtx;

    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
        { "counterparty-context-test.xml" };
    }

    @Override
    protected String getManifestLocation()
    {
        return "classpath:org/eclipse/ofmp/counterparty/test/MANIFEST.MF";
    }

    @Override
    protected String[] getTestBundlesNames()
    {
        List<String> list = new ArrayList<String>(Arrays.asList(super.getTestBundlesNames()));

        list.add("org.eclipse.ofmp,org.eclipse.ofmp.test,1.0.0-M1");
        list.add("org.eclipse.ofmp,org.eclipse.ofmp.security,1.0.0-M1");
        list.add("org.eclipse.ofmp,org.eclipse.ofmp.counterparty,1.0.0-M1");

        return list.toArray(new String[] {});
    }

    @Override
    protected void onSetUp() throws Exception
    {
        super.onSetUp();

        m_CounterpartyBundleAppCtx = (ApplicationContext) applicationContext.getBean("counterpartyBundleAppContext");

        m_DAO = (CounterpartyDAO) m_CounterpartyBundleAppCtx.getBean("counterpartyDAO");
    }

    public void testSelectBanks() throws Exception
    {
        List<Bank> result = m_DAO.selectBanks();

        assertNotNull(result);
        assertEquals(7, result.size());
    }

    public void testSelectCustomers() throws Exception
    {
        List<Customer> result = m_DAO.selectCustomers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    public void testFindCustomer() throws Exception
    {
        Customer customer = m_DAO.findCustomerById(9991);

        assertNotNull(customer);
        assertEquals("200306", customer.getName());
        assertEquals(Counterparty.Type.CUSTOMER, customer.getType());
        assertEquals("Wilmus Borzel", customer.getShortName());
    }

    public void testFindCustomerByName() throws Exception
    {
        Customer customer = m_DAO.findCustomerByName("2004400");

        assertNotNull(customer);
        assertEquals("2004400", customer.getName());
        assertEquals(Counterparty.Type.CUSTOMER, customer.getType());
        assertEquals("2004400", customer.getShortName());
    }

    public void testFindBank() throws Exception
    {
        Bank bank = m_DAO.findBankById(9996);

        assertNotNull(bank);
        assertEquals("BANCA ARNER LUGANO", bank.getName());
        assertEquals(Counterparty.Type.BANK, bank.getType());
        assertEquals(new Integer(5002160), bank.getBackofficeIdentification().getPortfolio());
        assertEquals("00.ARNECH", bank.getBackofficeIdentification().getId());

        FrontOfficeIdentification foi = bank.getIdentification("REUTERS");

        assertNotNull(foi);

        Assert.assertTrue(foi.contains("ARNE"));
    }

    public void testFindCounterparty() throws Exception
    {
        Counterparty bank = m_DAO.findBankById(9996);

        assertNotNull(bank);
        assertEquals("BANCA ARNER LUGANO", bank.getName());
        assertEquals(Counterparty.Type.BANK, bank.getType());
    }

    public void testFindByReutersBankCode() throws Exception
    {
        Bank c = m_DAO.findBankByReutersCode("REUTERS", "SOGS");

        assertNotNull(c);
        assertEquals(new Integer(9994), c.getId());
    }

    public void testCreateBank() throws Exception
    {
        Bank bank = CounterpartyFactory.createBank();

        bank.setName("My Bank");
        bank.getBackofficeIdentification().setPortfolio(9999999);
        bank.getBackofficeIdentification().setId("00.AAAGCV");

        Map<String, FrontOfficeIdentification> map = new HashMap<String, FrontOfficeIdentification>();

        FrontOfficeIdentification fid = new FrontOfficeIdentification("REUTERS");
        fid.add("ASDF");

        map.put("REUTERS", fid);

        bank.setFrontOfficeIdentifications(map);

        Counterparty createdCtpy = m_DAO.create(bank);
        bank.setId(createdCtpy.getId());

        Bank createdBank = m_DAO.createBank(bank);

        assertNotNull(createdCtpy);
        assertNotNull(createdBank);
        assertEquals(createdCtpy.getId(), createdBank.getId());
        assertEquals(createdBank, bank);
    }

    public void testCreateCustomer() throws Exception
    {
        Customer customer = CounterpartyFactory.createCustomer();

        customer.setName("My Bank");
        customer.setShortName("aShortName");

        Counterparty createdCtpy = m_DAO.create(customer);
        customer.setId(createdCtpy.getId());

        Customer createdCustomer = m_DAO.createCustomer(customer);

        assertNotNull(createdCtpy);
        assertNotNull(createdCustomer);
        assertEquals(createdCtpy.getId(), createdCustomer.getId());
        assertEquals(createdCustomer, customer);
    }

    public void testUpdate() throws Exception
    {
        Counterparty counterparty = m_DAO.findBankById(9996);

        counterparty.setName("NEW BANK NAME");

        Counterparty updated = m_DAO.updateCounterparty(counterparty);
        assertNotNull(updated);

        updated = m_DAO.findBankById(updated.getId());

        assertEquals(counterparty, updated);
    }

    public void testUpdateBank() throws Exception
    {
        Bank counterparty = m_DAO.findBankById(9996);

        counterparty.getBackofficeIdentification().setId("aApsysCode");

        Counterparty updated = m_DAO.updateBank(counterparty);
        assertNotNull(updated);

        updated = m_DAO.findBankById(updated.getId());

        assertEquals(counterparty, updated);
    }

    public void testUpdateCustomer() throws Exception
    {
        Customer counterparty = m_DAO.findCustomerById(9991);

        counterparty.setShortName("aShortName");

        Counterparty updated = m_DAO.updateCustomer(counterparty);
        assertNotNull(updated);

        updated = m_DAO.findCustomerById(updated.getId());

        assertEquals(counterparty, updated);
    }
}
