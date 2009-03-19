package org.eclipse.ofmp.configuration;

import java.io.File;
import java.io.FilenameFilter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class ConfigurationBundleActivator implements BundleActivator
{
    private ServiceTracker m_Tracker;
    private BundleContext m_Context;
    private ConfigurationAdmin m_ConfigurationAdmin;

    public void start(final BundleContext aContext) throws Exception
    {
        m_Context = aContext;
        m_Tracker = new ServiceTracker(aContext, ConfigurationAdmin.class.getName(), new ServiceTrackerCustomizer()
        {

            public Object addingService(ServiceReference aReference)
            {
                m_ConfigurationAdmin = (ConfigurationAdmin) aContext.getService(aReference);

                scanConfigurationDirectory(SystemEnvironment.CONF_DIR);
                
                return m_ConfigurationAdmin;
            }

            public void modifiedService(ServiceReference reference, Object service)
            {
            }

            public void removedService(ServiceReference reference, Object service)
            {
                m_ConfigurationAdmin = null;
                aContext.ungetService(reference);
            }
        });

        m_Tracker.open();
    }

    public void stop(BundleContext aContext) throws Exception
    {
        m_Tracker.close();
    }
    
    private void scanConfigurationDirectory(File aDirectory)
    {
        FilenameFilter filter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".properties") || name.endsWith(".conf");
            }
        };
        
        for(String fileName: aDirectory.list(filter))
        {
            File file = new File(aDirectory, fileName);
            if(file.isFile() && file.canRead())
            {
                ConfigurationFileObserver fo = new ConfigurationFileObserver(file, m_ConfigurationAdmin, m_Context);
                fo.observe();
            }
        }
    }
}
