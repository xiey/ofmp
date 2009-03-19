package org.eclipse.ofmp.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class ConfigurationFileObserver
{
    private String PROP_TAG = "ofmp.configuration.tag";
    
    private Logger log = Logger.getLogger(getClass());
    
    private File m_File;
    private ConfigurationAdmin m_ConfigurationAdmin;

    private BundleContext m_Context;

    public ConfigurationFileObserver(File aFile, ConfigurationAdmin aConfigurationAdmin, BundleContext aContext)
    {
        m_File = aFile;
        m_ConfigurationAdmin = aConfigurationAdmin;
        m_Context = aContext;
    }

    public void observe()
    {
        try
        {
            FileInputStream is = null;
            
            try
            {
                is = new FileInputStream(m_File);
                
                Properties properties = new Properties();
                properties.load(is);
                
                
                String pid=null, bundle=null;
                
                String tag = properties.getProperty(PROP_TAG);
                if(tag != null)
                {
                    int at = tag.indexOf('@');
                    if(at > 0)
                    {
                        pid = tag.substring(0, at);
                        bundle = tag.substring(at+1);
                    }else
                        pid = tag;

                    Configuration configuration = null;
                    
                    if(bundle != null)
                    {
                        String location = getLocation(bundle);
                        if(location != null)
                            configuration = m_ConfigurationAdmin.getConfiguration(pid, location);
                        else
                            log.warn("Undefined bundle: " + bundle + " (" + m_File.getAbsolutePath() + ")");
                    }
                    else
                        configuration = m_ConfigurationAdmin.getConfiguration(pid);
                    
                    if(configuration != null)
                        configuration.update(properties);
                    
                    properties.remove(PROP_TAG);
                }
            }
            finally
            {
                if(is != null)
                    is.close();
            }
        }
        catch(IOException aEx)
        {
            log.warn("Cannot read file: " + m_File);
        }
    }
    
    private String getLocation(String aBundle)
    {
        for (Bundle bundle : m_Context.getBundles())
        {
            if (bundle.getSymbolicName().equals(aBundle))
                return bundle.getLocation();
        }

        return null;
    }
}
