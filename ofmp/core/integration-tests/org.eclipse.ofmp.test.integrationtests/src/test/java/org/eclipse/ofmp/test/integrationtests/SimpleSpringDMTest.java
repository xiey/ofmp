package org.eclipse.ofmp.test.integrationtests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;
import org.eclipse.ofmp.test.TestService;
import org.osgi.framework.ServiceReference;

/**
 * General tests about OSGI Environment
 */
public class SimpleSpringDMTest extends AbstractOFMPSpringDMTest
{
    private final Logger log = Logger.getLogger(getClass());

	@Override
	protected boolean needClean() {
		return false;
	}
	
	@Override
	protected String getManifestLocation() {
		return "classpath:org/eclipse/ofmp/test/integrationtests/MANIFEST.MF";
	}

	protected String[] getTestBundlesNames() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(Arrays.asList(super.getTestBundlesNames()));
		
		result.add("org.eclipse.ofmp, org.eclipse.ofmp.security, 1.0.0-M1");
		result.add("org.eclipse.ofmp, org.eclipse.ofmp.test, 1.0.0-M1");
			
		return result.toArray(new String[]{});
	}
	
	public void testOSGiPlatformStarts() throws Exception
	{
		assertTrue(bundleContext != null);
		assertTrue(bundleContext.getBundles().length > 0);
	}
	
	public void testTestService() throws Exception {

		ServiceReference ref = bundleContext.getServiceReference(TestService.class.getName());
		assertNotNull("TestService service reference is null", ref);
		
		TestService testService = (TestService) bundleContext.getService(ref);
		assertNotNull("TestService is null", testService);

		try {
			testService.cleanUp();
			testService.initialize();
		} catch (Exception ex) {
			assertTrue(false);
			log.error(ex);
			throw ex;
		}
	}

}