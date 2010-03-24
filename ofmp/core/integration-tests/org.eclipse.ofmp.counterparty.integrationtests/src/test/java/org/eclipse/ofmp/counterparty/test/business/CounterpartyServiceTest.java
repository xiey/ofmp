package org.eclipse.ofmp.counterparty.test.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ofmp.counterparty.business.CounterpartyFactory;
import org.eclipse.ofmp.counterparty.business.CounterpartyService;
import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Counterparty;
import org.eclipse.ofmp.counterparty.dom.Customer;
import org.eclipse.ofmp.counterparty.dom.FrontOfficeIdentification;
import org.eclipse.ofmp.security.business.AuthenticationService;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;

public class CounterpartyServiceTest extends AbstractOFMPSpringDMTest
{
    private CounterpartyService m_CounterpartyService;

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
        System.setProperty("needclean", "true");
        System.setProperty("instancecounterpartyid", "9993");
        super.onSetUp();

        authenticateAs("ofmp-test-mo");
    }

    public void testCreateBank() throws Exception
    {
        Bank bank = CounterpartyFactory.createBank();

        bank.setName("My Bank");
        bank.getBackofficeIdentification().setPortfolio(9999999);
        bank.getBackofficeIdentification().setId("00.AAAGCV");

        FrontOfficeIdentification foi = new FrontOfficeIdentification("REUTERS");
        foi.add("AGCV");

        bank.addIdentification(foi);

        Counterparty createdCtpy = m_CounterpartyService.create(bank);

        Counterparty foundCtpy = m_CounterpartyService.findCounterparty(createdCtpy.getId());

        assertNotNull(createdCtpy);
        assertEquals(createdCtpy, foundCtpy);
    }

    public void testCreateCustomer() throws Exception
    {
        Customer customer = CounterpartyFactory.createCustomer();

        customer.setName("My Bank");
        customer.setShortName("aShortName");

        Counterparty createdCtpy = m_CounterpartyService.create(customer);

        Counterparty foundCtpy = m_CounterpartyService.findCounterparty(createdCtpy.getId());

        assertNotNull(createdCtpy);
        assertEquals(createdCtpy, foundCtpy);
    }

    public void testFindBank() throws Exception
    {
        assertNotNull(m_CounterpartyService.findCounterparty(9996));
    }

    public void testFindCounterparty() throws Exception
    {
        assertNotNull(m_CounterpartyService.findCounterparty(9996));
    }

    public void testFindCustomerByName() throws Exception
    {
        Customer c = m_CounterpartyService.findCustomerByName("2004400");

        assertNotNull(c);
        assertEquals("2004400", c.getName());
    }

    public void testFindCustomerByShortName() throws Exception
    {
        Customer c = m_CounterpartyService.findCustomerByShortName("2004400");

        assertNotNull(c);
        assertEquals("2004400", c.getShortName());
    }

    public void testFindBankByReutersCode() throws Exception
    {
        Counterparty c = m_CounterpartyService.findBankByExternalId("REUTERS", "SOGS");

        assertNotNull(c);
        assertEquals(new Integer(9994), c.getId());
    }

    public void testEnumerateBanks() throws Exception
    {
        assertEquals(7, m_CounterpartyService.enumerateBanks().size());
    }

    public void testEnumerateCounterparties() throws Exception
    {
        assertEquals(9, m_CounterpartyService.enumerateCounterparties().size());
    }

    public void testUpdateBank() throws Exception
    {
        Bank counterparty = (Bank) m_CounterpartyService.findCounterparty(9996);

        counterparty.getBackofficeIdentification().setId("someOtherApsysCode");

        Counterparty foundCounterparty = m_CounterpartyService.update(counterparty);

        // Various assertions
        assertEquals(foundCounterparty, m_CounterpartyService.findCounterparty(9996));
    }

    public void testUpdateCustomer() throws Exception
    {
        Customer counterparty = (Customer) m_CounterpartyService.findCounterparty(9991);

        counterparty.setShortName("someOtherShortName");

        Counterparty foundCounterparty = m_CounterpartyService.update(counterparty);

        // Various assertions
        assertEquals(foundCounterparty, m_CounterpartyService.findCounterparty(9991));
    }

    public void testCreateAndUpdateCustomer() throws Exception
    {
        Customer customer = CounterpartyFactory.createCustomer();

        customer.setName("My Bank");
        customer.setShortName("aShortName");

        Counterparty createdCtpy = m_CounterpartyService.create(customer);

        Counterparty foundCtpy = m_CounterpartyService.findCounterparty(createdCtpy.getId());

        foundCtpy.setName("My beautiful bank");

        Counterparty updatedCtpy = m_CounterpartyService.update(foundCtpy);

        // Various assertions
        assertEquals(updatedCtpy, foundCtpy);
    }

    // FIXME InstanceCounterparty should be injected before test is launched
    // public void testGetInstanceCounterparty() throws Exception
    // {
    // assertNotNull(m_CounterpartyService.getInstanceCounterparty());
    // }

    @Override
    protected void onTearDown() throws Exception
    {
        // Required after each test because of internal counterparties caching
        // getApplicationContext().close();

        super.onTearDown();
    }

    public void setAuthenticationService(AuthenticationService aAuthenticationService)
    {
        m_AuthenticationService = aAuthenticationService;
    }

    public void setCounterpartyService(CounterpartyService aService)
    {
        m_CounterpartyService = aService;
    }

}
