package org.eclipse.ofmp.currency.test.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ofmp.currency.business.CurrencyService;
import org.eclipse.ofmp.security.business.AuthenticationService;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;

public class CurrencyServiceTest extends AbstractOFMPSpringDMTest
{
    private CurrencyService m_CurrencyService;

	@Override
	protected String[] getConfigLocations() {
    	return new String[]{ "currency-context-test.xml" };
	}

	@Override
	protected String getManifestLocation() {
		return "classpath:org/eclipse/ofmp/currency/test/MANIFEST.MF";
	}

    @Override
    protected String[] getTestBundlesNames() {
    	List<String> list  = new ArrayList<String>(Arrays.asList(super.getTestBundlesNames()));
    	
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.test,1.0.0-M1");
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.security,1.0.0-M1");
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.currency,1.0.0-M1");
    	    	
    	return list.toArray(new String[]{});
    }
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();

        authenticateAs("ofmp-test-mo");
	}

    public void testFind() throws Exception
    {
        assertNotNull(m_CurrencyService.findCurrency("USD"));
        assertNotNull(m_CurrencyService.findCurrency("EUR"));
    }

    public void testEnumerate() throws Exception
    {
        assertEquals(26, m_CurrencyService.enumerate(false).size());
    }

    public void testEnumerateExcludeReportingCurrency() throws Exception
    {
        assertEquals(25, m_CurrencyService.enumerate(true).size());
    }

	public void setCurrencyService(CurrencyService aCurrencyService) {
		m_CurrencyService = aCurrencyService;
	}

	public void setAuthenticationService(AuthenticationService aAuthenticationService) {
		m_AuthenticationService = aAuthenticationService;
	}
 	
}
