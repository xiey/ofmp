package org.eclipse.ofmp.currency.test.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.ofmp.currency.dao.CurrencyDAO;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;
import org.springframework.context.ApplicationContext;

public class CurrencyDAOTest extends AbstractOFMPSpringDMTest
{
	private CurrencyDAO m_DAO;
	private ApplicationContext m_CurrencyBundleAppCtx;
	
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

		m_CurrencyBundleAppCtx = (ApplicationContext) applicationContext.getBean("currencyBundleAppContext");
		
		m_DAO = (CurrencyDAO) m_CurrencyBundleAppCtx.getBean("currencyDAO");
	}
    
    public void testSelect() throws SQLException
    {
        Collection<Currency> c = m_DAO.select();

        assertTrue(c.size() > 0);
    }

    
    public void testFind() throws SQLException
    {
        Currency c = m_DAO.find("EUR");

        assertEquals(c.getISOCode(), "EUR");
        assertEquals(c.getName(), "Euro");
        assertTrue(c.isCLS());
    }
}
