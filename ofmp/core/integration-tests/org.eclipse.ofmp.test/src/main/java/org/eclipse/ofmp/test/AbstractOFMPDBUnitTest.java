package org.eclipse.ofmp.test;

import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;

public abstract class AbstractOFMPDBUnitTest extends AbstractConfigurableBundleCreatorTests
{

	@Override
    protected void onSetUp() throws Exception
    {
		
        if (needClean())
        {
    		TestService testService = (TestService) bundleContext.getService(bundleContext.getServiceReference(TestService.class.getName()));
    		
    		if (testService == null)
    			return;

        	String[] DBUnitDatasetFiles = getDBUnitDatasetFiles();

        	if (DBUnitDatasetFiles.length == 0) {
    			testService.cleanUp();
    			testService.initialize();
    		}
    		else
    		{
    			testService.cleanUp(DBUnitDatasetFiles);
    			testService.initialize(DBUnitDatasetFiles);
    		}
        }
    }

    
    @SuppressWarnings("boxing")
    protected boolean needClean()
    {
        String needCleanProp = System.getProperty("needclean");

        if (needCleanProp != null)
            return Boolean.valueOf(needCleanProp);

        return true;
    }

    /**
     * @return Returns the list of DBUnit data set files used to cleanup and initialize the database
     */
    protected String[] getDBUnitDatasetFiles()
    {
    	return new String[]{};
    }

    @Override
    protected String[] getTestBundlesNames() {
    	return new String[] {"javax.jms,com.springsource.javax.jms,1.1.0",
    			"javax.transaction,com.springsource.javax.transaction,1.1.0",
    			"javax.resource,com.springsource.javax.resource,1.5.0",
    			"org.eclipse.ofmp,com.oracle.ojdbc,10.2.0",
    			"org.apache.ibatis,com.springsource.com.ibatis,2.3.0.677",
    			"net.sourceforge.cglib,com.springsource.net.sf.cglib,2.1.3",
    			"org.apache.commons,com.springsource.org.apache.commons.collections,3.2.0",
    			"org.eclipse.ofmp,org.apache.geronimo.transaction,1.0.0",
    			"org.eclipse.ofmp,org.eclipse.ofmp.lib.jencks,1.0.0",
    			"org.eclipse.ofmp,org.eclipse.ofmp.transaction,1.0.0-M1",
    			"org.eclipse.ofmp,org.eclipse.ofmp.datasource.oracle,1.0.0-M1"};
    }

}
