package org.eclipse.ofmp.security.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ofmp.security.business.AuthenticationService;
import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class AuthenticationTest extends AbstractOFMPSpringDMTest
{
	private AuthenticationService m_AuthenticationService;
	
    public void testAuthentication()	
    {
        UsernamePasswordAuthenticationToken aIn = new UsernamePasswordAuthenticationToken("ofmp-fx-dealer",
                "87654321");

        OFMPAuthenticationToken auth = (OFMPAuthenticationToken) m_AuthenticationService.authenticate(aIn);

        assertNotNull(auth);
        assertNotNull(auth.getUser());
        assertNotNull(auth.getDealer());
    }
    
	@Override
	protected String getManifestLocation() {
		return "classpath:org/eclipse/ofmp/security/test/MANIFEST.MF";
	}
    
    @Override
    protected String[] getTestBundlesNames() {
    	List<String> list  = new ArrayList<String>(Arrays.asList(super.getTestBundlesNames()));
    	
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.test,1.0.0-M1");
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.security,1.0.0-M1");
    	    	
    	return list.toArray(new String[]{});
    }

    @Override
    protected String[] getConfigLocations() {
    	return new String[]{ "security-context-test.xml" };
    }

	public void setAuthenticationService(AuthenticationService aAuthenticationService) {
		m_AuthenticationService = aAuthenticationService;
	}
    
}
